import os
import re
import epub
import nltk
import pysolr
from dateutil.parser import parse

html_pattern = re.compile('<.*?>')


def remove_husk(text):
    css_pattern = re.compile('{.*?}')
    text_without_html = html_pattern.sub(r'', text)
    text_without_css = css_pattern.sub(r'', text_without_html)
    text_without_unnecessary_punctuation = re.sub('\s+', ' ', text_without_css).strip()
    text_without_annotated_words = text_without_unnecessary_punctuation \
        .replace('Cover @page body ', '') \
        .replace('@page', '')
    return text_without_annotated_words


nltk.download('punkt')
book_solr_connection = pysolr.Solr('http://localhost:8983/solr/book', timeout=10)
cover_solr_connection = pysolr.Solr('http://localhost:8983/solr/cover', timeout=10)

epub_data_directory = 'all_data'
text_data_directory = 'text'
sentences_per_page = 21
data_directory_files = os.listdir(epub_data_directory)
epub_directory_files = filter(lambda x: x.endswith('.epub'), data_directory_files)
if not os.path.exists(text_data_directory):
    os.makedirs(text_data_directory)
for epub_file in epub_directory_files:
    print(epub_file)
    book = epub.open_epub(epub_data_directory + '/' + epub_file)
    date, creator, title = '', '', ''
    if len(book.opf.metadata.dates) > 0:
        date, _ = book.opf.metadata.dates[0]
        if date not in ['', 'NONE']:
            date = parse(date, fuzzy=True).year
        else:
            date = ''
    if len(book.opf.metadata.creators) > 0:
        creator, _, _ = book.opf.metadata.creators[0]
    if len(book.opf.metadata.titles) > 0:
        title, _ = book.opf.metadata.titles[0]
    description = book.opf.metadata.description
    if not description:
        description = ''
    cover_solr_connection.add([{
        'title': title,
        'creator': creator,
        'date': date,
        'description': creator + ' ' + creator[0] + ' ' + title + ' ' + str(date) + ' ' + ' '.join(html_pattern.sub(r'', description.replace('SUMMARY:', '').replace('EDITORIAL REVIEW:', '').replace('Unknown', '').replace('\n', '')).split()),
        'file': epub_file
    }])
    data = ''
    for item_id, _ in book.opf.spine.itemrefs:
        item = book.get_item(item_id)
        data += book.read_item(item).decode("utf-8")
    data = remove_husk(data)
    sentences = nltk.sent_tokenize(data)
    pages = [sentences[x:x + sentences_per_page] for x in range(0, len(sentences), sentences_per_page)]
    page_number = 1
    for page in pages:
        book_solr_connection.add([{
            'file': title,
            'page': page_number,
            'content': ' '.join(page)
        }])
        page_number += 1
book_solr_connection.commit()
cover_solr_connection.commit()

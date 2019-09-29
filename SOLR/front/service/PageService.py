import re
import nltk
import epub
import json


def remove_husk(text):
    html_pattern = re.compile('<.*?>')
    css_pattern = re.compile('{.*?}')
    text_without_html = html_pattern.sub(r'', text)
    text_without_css = css_pattern.sub(r'', text_without_html)
    text_without_unnecessary_punctuation = re.sub('\\s+', ' ', text_without_css).strip()
    text_without_annotated_words = text_without_unnecessary_punctuation \
        .replace('Cover @page body ', '') \
        .replace('@page', '')
    return text_without_annotated_words


class PageService:
    def __init__(self, epub_data_directory, sentences_per_page):
        nltk.download('punkt')
        self._epub_data_directory = epub_data_directory
        self._sentences_per_page = sentences_per_page

    def get_page(self, filename, page):
        book = epub.open_epub(self._epub_data_directory + '/' + filename)
        data = ''
        for item_id, _ in book.opf.spine.itemrefs:
            item = book.get_item(item_id)
            data += book.read_item(item).decode("utf-8")
        data = remove_husk(data)
        sentences = nltk.sent_tokenize(data)
        pages = [sentences[x:x + self._sentences_per_page] for x in range(0, len(sentences), self._sentences_per_page)]
        return json.dumps(
            {"text": ' '.join(pages[page]), "pages": len(pages), "page": page})

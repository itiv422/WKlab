import pysolr
import epub
import os

book_solr_connection = pysolr.Solr('http://localhost:8983/solr/book', timeout=10)
cover_solr_connection = pysolr.Solr('http://localhost:8983/solr/cover', timeout=10)
epub_data_directory = 'data'
data_directory_files = os.listdir(epub_data_directory)
epub_directory_files = filter(lambda x: x.endswith('.epub'), data_directory_files) 

for epub_file in epub_directory_files:
    print(epub_file)
    book = epub.open(epub_data_directory + '/' +epub_file)
    title, creator, date = '', '', ''
    if len(book.opf.metadata.titles) > 0:
        title, _ = book.opf.metadata.titles[0]
    if len(book.opf.metadata.creators) > 0:
        creator, _, _ = book.opf.metadata.creators[0]
    if len(book.opf.metadata.dates) > 0:
        date = book.opf.metadata.dates[0][0]
    description = book.opf.metadata.description
    cover_solr_connection.add([{
        'title': title,
        'creator': creator,
        'date': date,
        'description': description,
        'file': epub_file
    }])
    for item_id, _ in book.opf.spine.itemrefs:
        item = book.get_item(item_id)
        data = book.read_item(item)
        book_solr_connection.add([{
            'file': title,
            'page': item_id,
            'content': data
        }])
book_solr_connection.commit()
cover_solr_connection.commit()
import pysolr
import epub
import os

solr = pysolr.Solr('http://localhost:8983/solr/', timeout=10, auth=<type of authentication>)
epub_data_directory = 'data'
data_directory_files = os.listdir(epub_data_directory)
epub_directory_files = filter(lambda x: x.endswith('.epub'), data_directory_files) 

for epub_file in epub_directory_files:
    book = epub.open(epub_data_directory + '/' +epub_file)
    title, creator, date = '', '', ''
    if len(book.opf.metadata.titles) > 0:
        title, _ = book.opf.metadata.titles[0]
    if len(book.opf.metadata.creators) > 0:
        creator, _, _ = book.opf.metadata.creators[0]
    if len(book.opf.metadata.dates) > 0:
        date = book.opf.metadata.dates[0]
    description = book.opf.metadata.description
    solr.add({
        'title': title,
        'creator': creator,
        'date': date,
        'description': description,
        'file': epub_file
    })

    for item_id, linear in book.opf.spine.itemrefs:
        item = book.get_item(item_id)
        if linear:
            print('Linear item ' + item.href)
        else:
            print( 'Non-linear item ' + item.href)
        data = book.read_item(item)
        solr.add({
            'file': title,
            'page': item_id,
            'cantent': content
        })
from flask import Flask, redirect, request, send_from_directory
from service.PageService import PageService
from service.SolrService import SolrService

app = Flask(__name__, static_url_path='')
pageService = PageService('static/books', 21)
solrService = SolrService('http://localhost:8983/solr/')  # solrserver


@app.route('/search', methods=['POST'])
def solr_search():
    return solrService.search(request.json)


@app.route('/page', methods=['POST'])
def get_page():
    return pageService.get_page(request.json['file'], request.json['page'])


@app.route('/<path:path>')
def send_static(path):
    return send_from_directory('static', path)


@app.route('/')
def index():
    return redirect("index.html")


if __name__ == '__main__':
    app.run(debug=True, port='5001')  # , host='front'

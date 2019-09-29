import requests


def generate_filter(entities, field_name):
    filters = []
    for entity_filter in entities:
        filters.append(field_name + ':"' + entity_filter + '"')
    final_filter = ' AND(' + generate_query(filters) + ')'
    if len(final_filter) == 6:
        final_filter = ''
    return final_filter


def generate_query(query_list):
    query = ''
    if len(query_list) == 1:
        query = query_list[0]
    if len(query_list) > 1:
        for fl in query_list:
            query = query + fl + ' OR '
        query = query[:-4]
    return query


class SolrService:
    def __init__(self, solr_server):
        self._solr_server = solr_server

    def search(self, request):
        facet = 'facet.field=date&facet.field=creator&facet.mincount=1&facet=on'
        page = '&rows=10&start=' + str(10 * request['page'])
        filter_dates, filter_creators = '', ''
        if 'dates' in request:
            filter_dates = generate_filter(request['dates'], 'date')
        if 'creators' in request:
            filter_creators = generate_filter(request['creators'], 'creator')
        search = requests.get(self._solr_server + 'cover/select?' + facet + '&q=description:"' + request['search']
                              + '"~20' + filter_dates + filter_creators + page)
        if search.json()['response']['numFound']:
            return search.json()
        else:
            search = requests.get(self._solr_server + 'book/select?q=content:"' + request['search']
                                  + '"~20&group=true&group.field=content')
            title_list = []
            for group in search.json()['grouped']['content']['groups']:
                title_list.append('title:"' + group['doclist']['docs'][0]['file'] + '"')
            if len(title_list) == 0:
                return {"response": {"numFound": 0}}
            q = generate_query(title_list)
            search = requests.get(self._solr_server + 'cover/select?q=(' + q + ')' + filter_dates + filter_creators
                                  + '&' + facet)
            return search.json()

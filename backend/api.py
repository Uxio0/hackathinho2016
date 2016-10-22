#!/usr/bin/env python3
import json
from flask import Flask, Response
from bson import json_util
from electricidad import *

app = Flask(__name__)

energy_types = {
    'solar': 10205,
    'hidraulic': 10035,
    'eolic': 10037
}

populate_all(energy_types.values())


@app.route('/')
def index():
    return "Hello, World!"


@app.route('/api/energy/<energy_type>', methods=['GET'])
def get_task(energy_type):
    if energy_type not in energy_types:
        abort(404)
    result = get_info_and_cities(energy_types[energy_type])
    #return jsonify(result)
    return Response(json.dumps(result,
                               default=json_util.default,
                               ensure_ascii=False),
                    mimetype='application/json')

if __name__ == '__main__':
    #app.run(debug=True)
    #app.run()
    app.run('0.0.0.0')

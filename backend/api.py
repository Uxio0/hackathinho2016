#!/usr/bin/env python3
import json
from flask import Flask, Response
from bson import json_util
from electricity import *

app = Flask(__name__)

energy_types = {
    'eolic': 10037,
    'hidraulic': 10035,
    'solar': 10205
}

populate_all(energy_types.values())


@app.route('/')
def index():
    return "Hello, World!"


@app.route('/api/energy/<energy_type>', methods=['GET'])
def get_energy(energy_type):
    if energy_type not in energy_types:
        abort(404)
    result = get_from_mongo(energy_types[energy_type])
    #return jsonify(result)
    return Response(json.dumps(result,
                               default=json_util.default,
                               ensure_ascii=False),
                    mimetype='application/json')


@app.route('/api/energy/<energy_type>/total', methods=['GET'])
def get_energy_sum(energy_type):
    if energy_type not in energy_types:
        abort(404)
    result = get_sum_from_mongo(energy_types[energy_type])
    return Response(json.dumps(result,
                               default=json_util.default,
                               ensure_ascii=False),
                    mimetype='application/json')


if __name__ == '__main__':
    #app.run(debug=True)
    #app.run()
    app.run('0.0.0.0')

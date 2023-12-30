import csv
import glob

node_count = 0

def buildQuery(row):
    global node_count
    result = ''

    from_node_var = f'f{node_count}'
    to_node_var = f'f{node_count+1}'

    result += f'MERGE ({from_node_var}:{row["from_node_type"].capitalize()} {{name: "{row["from_node"]}_{row["from_node_ts"]}"}})\n'
    result += f'SET {from_node_var}.key="{row["from_node_key"]}", '
    if "from_node_note" in row:
        result += f'{from_node_var}.note="{row["from_node_note"]}", '
    result += f'{from_node_var}.ts={row["from_node_ts"]}\n'

    result += f'MERGE ({to_node_var}:{row["to_node_type"].capitalize()} {{name: "{row["to_node"]}_{row["to_node_ts"]}"}})\n'
    result += f'SET {to_node_var}.key="{row["to_node_key"]}", '
    if "to_node_note" in row:
        result += f'{to_node_var}.note="{row["to_node_note"]}", '
    result += f'{to_node_var}.ts={row["to_node_ts"]}\n'

    result += f'MERGE ({from_node_var})-[:{row["relation"].upper()}]->({to_node_var})\n'
    
    node_count += 2

    return result


csv_paths = glob.glob('yasuda_data/data_for_neo4j*.csv')

cypher_file = open('src/test/resources/data/yasuda_data.cypher', 'w')
cypher_file.write('MATCH (n) DETACH DELETE n;\n\n')

for path in csv_paths:
    with open(path, newline='') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            query = buildQuery(row)
            cypher_file.write(query + '\n')

cypher_file.close()
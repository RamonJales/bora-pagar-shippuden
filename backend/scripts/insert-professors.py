import json
import os
from psycopg2.extras import execute_values
import psycopg2
import sys
from dataclasses import dataclass


department_memo = {}


def department_link(code):
    return f"https://sigaa.ufrn.br/sigaa/public/departamento/portal.jsf?id={code}"

def get_or_insert_department(conn, code, name):
    if code in department_memo:
        return department_memo[code]

    with conn.cursor() as cur:
        cur.execute("""
                    INSERT INTO department (code, name, link)
                    VALUES (%s, %s, %s)
                    RETURNING id
                    """, (code, name, department_link(code)))
        
        result = cur.fetchone()
        conn.commit()

        department_memo[code] = result[0]
        return result[0]


def insert_professor(conn, name, siape_code, image_url, department_id):
    with conn.cursor() as cur:
        cur.execute("""
                    INSERT INTO professor (name, siape_code, image_url, department_id)
                    VALUES (%s, %s, %s, %s)
                    ON CONFLICT (siape_code) DO NOTHING
                    """, (name, siape_code, image_url, department_id))
        conn.commit()

def main(file):
    try:
        with open(file, 'r', encoding='utf-8') as f:
            professors = json.load(f)

            conn_url = os.environ.get("DB_URL", "postgresql://postgres:password@localhost:5432/borapagar")

            conn = psycopg2.connect("postgresql://postgres:password@localhost:5432/borapagar")
            for professor in professors:
                dept_code = int(professor['department']['code'])
                dept_name = professor['department']['name']

                prof_name = professor['name']
                prof_siape_code = professor['siapeCode']
                prof_image_url = professor['photoUrl']
                dept_id = get_or_insert_department(conn, dept_code, dept_name)

                insert_professor(conn, prof_name, prof_siape_code, prof_image_url, dept_id)

        conn.commit()
    except json.JSONDecodeError as e:
        print(f"Error decoding JSON: {e}", file=sys.stderr)
    except psycopg2.Error as e:
        print(f"Database error: {e}", file=sys.stderr)
    except Exception as e:
        print(e.with_traceback())
        print(f"Error: {e}", file=sys.stderr)

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python insert-professors.py <dados.json>", file=sys.stderr)
        sys.exit(1)

    main(sys.argv[1])

# pip install psycopg2
import json
import os
from psycopg2.extras import execute_values
from typing import Optional
import psycopg2
import sys
from dataclasses import dataclass

@dataclass
class Subject:
    name: str
    code: str
    hours: int
    syllabus: Optional[str]

def decode_subject(dct):
    try:
        name = dct.get("nome")
        code = dct.get("codigo")
        ch_total_str = dct.get("ch_total")
        syllabus = dct.get("ementa")

        if not name or not code or not ch_total_str:
            return None
        
        try:
            ch_total = int(ch_total_str)
        except ValueError:
            ch_total = 0

        return Subject(name[:255], code[:255], ch_total, syllabus)

    except Exception as e:
        print(f"Error decoding subject: {e}", file=sys.stderr)
        return None

def main(file):
    try:
        with open(file, 'r', encoding='utf-8') as f:
            subjects = json.load(f, object_hook=decode_subject)

        subjects_json = [(subject.name, subject.code, subject.hours, subject.syllabus, "NOW()") for subject in subjects if subject is not None]

        conn_url = os.environ.get("DB_URL","postgresql://postgres:password@localhost:5432/borapagar")

        conn = psycopg2.connect(conn_url)
        cur = conn.cursor()

        execute_values(cur, "INSERT INTO subject (name, code, hours, syllabus, created_at) VALUES %s", subjects_json)

        conn.commit()

        print(f"Inserted {len(subjects_json)} records successfully.")

    except json.JSONDecodeError as e:
        print(f"Error decoding JSON: {e}", file=sys.stderr)
    except psycopg2.Error as e:
        print(f"Database error: {e}", file=sys.stderr)
    except Exception as e:
        print(f"Error: {e}", file=sys.stderr)

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python insert-subject.py <data.json>", file=sys.stderr)
        sys.exit(1)

    main(sys.argv[1])

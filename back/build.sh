set -o errexit

pip install -r requirements.txt

python manage.py collectstatic --no input
Python manage.py migrate 
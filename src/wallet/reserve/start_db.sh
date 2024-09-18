docker build -t postgres-image .

docker volume create pgdata

docker run -d \
  --name postgres-container \
  -e POSTGRES_DB=wallet \
  -e POSTGRES_USER=qwerty \
  -e POSTGRES_PASSWORD=qwerty \
  -p 5432:5432 \
  -v pgdata:/var/lib/postgresql/data \
  postgres:16
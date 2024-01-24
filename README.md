# Как запустить
* Забилдить каждый микросервис, чтобы у каждого был jar в target
* Запустить docker-compose up на docker-compose.yml
* Подождать пока все сервисы заработают
* С помощью localhost:8070/render/create?width=1920&height=1081&minX=80&minY=80&maxX=1080&maxY=1080 можно получить к изображению с объектами
* С помощью localhost:8070/eureka можно получить доступ к эврике
* В папке frontend находится клиент с использованием leafleat

![](https://img.shields.io/badge/Java-%3E%3D%208-orange)
![](https://img.shields.io/badge/Maven-3-red)
![](https://img.shields.io/badge/PostgreSQL-%3E%3D%209-informational)
![](https://img.shields.io/badge/-JDBC-blue)
![](https://img.shields.io/badge/-H2%20-blueviolet)
![](https://img.shields.io/badge/-Liquibase-blue)
![](https://img.shields.io/badge/JUnit-%3E%3D%204-yellowgreen)
![](https://img.shields.io/badge/-checkstyle-lightgrey)

# job4j_tracker
Проект "Система заявок - трекер" представляет собой базовое консольное CRUD-приложение<br>
и демонстрирует принципы ООП.<br>

Пользователю отображается меню с возможностями программы.<br>

![](02_images/tracker.jpg) <br>

## Как запустить приложение

что бы запустить приложение необходима программа Docker Compose

Проверяем наличие программы и, если её нет, устанавливаем.

[Установка Docker Compose](https://github.com/IvanPavlovets/job4j_mock/blob/master/01_installation/INSTALLATION.md)

### Запуск приложения
_Примечание: Все команды выполняем в терминале Linux_<br>
<br>
Склонируйте проект:
```
git clone https://github.com/IvanPavlovets/job4j_tracker.git
```
Перейдите в папку проекта:
```
cd job4j_tracker
```
Выполните команду создание образов проекта:
```
docker-compose build
```
или запустите все образы проекта (предварительно, выполниться команда создания):
```
docker-compose up -d
```
Запустим приложение:
```
docker-compose run job4j_tracker
```
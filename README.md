# job4j_todo
# Проект "Список задач"
## Приложение представляет собой страницу со списком заданий

В таблице отображаем имя, дату создания и состояние (выполнено или нет)

- На странице со списком добавить кнопку "Добавить задание"
- На странице со списком добавить три ссылки: "Все", "Выполненные", "Новые"

При переходе по ссылкам в таблице нужно отображать: все задания, только выполненные или только новые

- При клике на задание переходим на страницу с подробным описанием задания
- На странице с подробным описанием добавить кнопки: "Выполнено", "Редактировать", "Удалить"
- Если нажали на кнопку выполнить, то задание переводится в состояние выполнено
- Кнопка "Редактировать" переводит пользователя на отдельную страницу для редактирования
- Кнопка "Удалить" удаляет задание и переходит на список всех заданий

## Technology Stack
- Java 17
- PostgreSQL
- Springframework.boot 2.7.6
- Hibernate
- Lombok
- H2database
- Liquibase

## Running the Project

- Создать базу данных под названием 'todo' в программе PostgreSQL
- Проверить настройки доступа к базе данных в следующих файлах:
-- db/liquibase.properties
-- src/main/resources/hibernate.cfg.xml
- Перед началом выполнить команду liquibase:update
- Запустить метод Main в корне проекта
- Открыть браузер и запустить страницу http://localhost:8080/

## Requirements for the environment
- Java 17
- PostgreSQL 15.2
- Apache Maven 3.8.4

### All tasks
https://github.com/voldomarich/job4j_todo/blob/master/All%20tasks.png
https://github.com/voldomarich/job4j_todo/blob/master/List.png

### Adding a new task
https://github.com/voldomarich/job4j_todo/blob/master/Create%20task.png

### Editing a task
https://github.com/voldomarich/job4j_todo/blob/master/Task.png

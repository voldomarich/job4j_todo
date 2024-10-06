# job4j_todo
# Проект "Список задач"
## Приложение представляет собой страницу со списком заданий

В таблице отображаем имя, дату создания и состояние (выполнено или нет)
- На странице со списком добавить кнопку "Добавить задание".
- На странице со списком добавить три ссылки: "Все", "Выполненные", "Новые". 
- 
При переходе по ссылкам в таблице нужно отображать: все задания, только выполненные или только новые.
- При клике на задание переходим на страницу с подробным описанием задания.
- На странице с подробным описанием добавить кнопки: "Выполнено", "Редактировать", "Удалить".
- Если нажали на кнопку выполнить, то задание переводится в состояние выполнено.
- Кнопка "Редактировать" переводит пользователя на отдельную страницу для редактирования.
- Кнопка "Удалить" удаляет задание и переходит на список всех заданий.

## Technology Stack
- Java 17
- PostgreSQL
- springframework.boot 2.7.6
- Hibernate
- Lombok
- H2database
- Liquibase
- 
## Running the Project
-Create a database named 'todo' in PostgreSQL.
-Check the database connection settings in the following files:
--db/liquibase.properties
--src/main/resources/hibernate.cfg.xml
-Before starting, run the command liquibase:update.
-Run the main method.
-Open the browser at http://localhost:8080/.

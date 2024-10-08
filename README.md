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

- Создать базу данных под названием 'tasks' в программе PostgreSQL
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
![AllTasks1.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FAllTasks1.png)
![AllTasks2.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FAllTasks2.png)

### Adding a new task
![CreatingTask1.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FCreatingTask1.png)
![CreatingTask2.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FCreatingTask2.png)
![CreatingTask3.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FCreatingTask3.png)

### Editing a task
![EditingTask1.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FEditingTask1.png)
![EditingTask2.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FEditingTask2.png)
![EditingTask3.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FEditingTask3.png)

### Marking a task as done
![MarkingTaskAsDone1.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FMarkingTaskAsDone1.png)
![MarkingTaskAsDone2.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FMarkingTaskAsDone2.png)
![MarkingTaskAsDone3.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FMarkingTaskAsDone3.png)

### Deleting a task
![DeletingTask1.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FDeletingTask1.png)
![DeletingTask2.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FDeletingTask2.png)
![DeletingTask3.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FDeletingTask3.png)

### List Of Done Tasks
![ListOfDone.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FListOfDone.png)

### List Of Undone Tasks
![ListOfNew.png](src%2Fmain%2Fresources%2Ftemplates%2Fimages%2FListOfNew.png)

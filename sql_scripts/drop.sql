---Удаление таблицы базы данных

drop type if exists Доступные_организации_enum cascade;

drop type if exists Статус_заказа_enum cascade;

drop type if exists Статус_продукции_в_заказе_enum cascade;

drop type if exists Отправитель_enum cascade;

drop type if exists Должность_enum cascade;

drop type if exists Состояние_оборудования_enum cascade;

drop type if exists Форма_сотрудничества_enum cascade;

drop type if exists Состояние_сотрудничества_enum cascade;

drop table if exists Служба_поддержки cascade;
drop table if exists Адрес_службы_поддержки cascade;

drop table if exists Расписание_консультантов cascade;

drop table if exists Консультант cascade;

drop table if exists Клиент cascade;
drop table if exists Организация_клиента cascade;

drop table if exists Заказ cascade;

drop table if exists Сообщение cascade;

drop table if exists Тип_продукции cascade;
drop table if exists Название_продукции cascade;
drop table if exists Описание_продукции cascade;

drop table if exists Продукция_в_заказе cascade;

drop table if exists Завод cascade;
drop table if exists Адрес_завода cascade;

drop table if exists Склад_готовой_продукции cascade;
drop table if exists Адрес_склада_готовой_продукции cascade;

drop table if exists Готовая_продукция cascade;

drop table if exists Склад_сырья cascade;
drop table if exists Адрес_склада_сырья cascade;

drop table if exists Материалы cascade;

drop table if exists Тип_материала cascade;
drop table if exists Название_материала cascade;
drop table if exists Описание_материала cascade;

drop table if exists Организация cascade;
drop table if exists Название_организации cascade;

drop table if exists Сотрудничество cascade;

drop table if exists Цех cascade;

drop table if exists Расписание_сотрудников cascade;

drop table if exists Сотрудник cascade;

drop table if exists Оборудование cascade;

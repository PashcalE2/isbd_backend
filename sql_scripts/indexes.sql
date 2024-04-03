create index h_idx_Адрес_службы_поддержки on "Адрес_службы_поддержки" using hash(адрес);

create index h_idx_Организация_клиента on "Организация_клиента" using hash(название);

create index h_idx_Заказ on "Заказ" using hash(ид, ид_консультанта, ид_клиента);

create index h_idx_Сообщение on "Сообщение" using hash(ид, ид_заказа);

create index h_idx_Тип_продукции on "Тип_продукции" using hash(ид);
create index h_idx_Название_продукции on "Название_продукции" using hash(ид_типа);
create index h_idx_Описание_продукции on "Описание_продукции" using hash(ид_типа);

create index h_idx_Продукция_в_заказе on "Продукция_в_заказе" using hash(ид_заказа, ид_типа);

create index h_idx_Завод on "Завод" using hash(ид);


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

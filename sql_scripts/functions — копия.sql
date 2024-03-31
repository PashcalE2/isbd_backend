/*
Обозначения:
	- Функция:
		(1) p <=> procedure
		(1) f <=> function
		(2) i <=> insert
		(2) u <=> update
		(2) d <=> delete
        (2) g <=> get

		- пример: function_insert_Цех <=> f_i_Цех
*/

/*
    Функции создания сущностей с уникальными параметрами
*/

-- Функция создания экземпляра службы поддержки
create or replace function f_i_Служба_поддержки(
    arg_название text,
    arg_номер_телефона varchar[20],
    arg_email varchar[64],
    arg_адрес text
)
returns int as $$
declare
    var_ид_службы_поддержки int;
begin
    insert into "Служба_поддержки" (название, номер_телефона, email)
    values (arg_название, arg_номер_телефона, arg_email)
    returning ид into var_ид_службы_поддержки;

    -- Добавить информацию об уникальном адресе службы поддержки
    insert into "Адрес_службы_поддержки" (ид_службы_поддержки, адрес)
    values (var_ид_службы_поддержки, arg_адрес);

    return var_ид_службы_поддержки;
end;
$$ language plpgsql;


-- Функция создания экземпляра клиента
create or replace function f_i_Клиент(
    arg_номер_телефона varchar[20],
    arg_email varchar[64],
    arg_пароль varchar[64],
    arg_название "Доступные_организации_enum"
)
returns int as $$
declare
    var_ид_клиента int;
begin
    insert into "Клиент" (номер_телефона, email, пароль)
    values (arg_номер_телефона, arg_email, arg_пароль)
    returning ид into var_ид_клиента;

    -- Добавить информацию об уникальном названии организации
    insert into "Организация_клиента" (ид_клиента, название)
    values (var_ид_клиента, arg_название);

    return var_ид_клиента;
end;
$$ language plpgsql;


-- Функция создания экземпляра организации
create or replace function f_i_Организация(
    arg_номер_телефона varchar[20],
    arg_email varchar[64],
    arg_название "Доступные_организации_enum"
)
returns int as $$
declare
    var_ид_организации int;
begin
    insert into "Организация" (номер_телефона, email)
    values (arg_номер_телефона, arg_email)
    returning ид into var_ид_организации;

    -- Добавить информацию об уникальном названии организации
    insert into "Название_организации" (ид_организации, название)
    values (var_ид_организации, arg_название);

    return var_ид_организации;
end;
$$ language plpgsql;


-- Функция создания экземпляра типа продукции
create or replace function f_i_Тип_продукции(
    arg_цена real,
    arg_название text,
    arg_описание text
)
returns int as $$
declare
    var_ид_типа_продукции int;
begin
    insert into "Тип_продукции" (цена)
    values (arg_цена)
    returning ид into var_ид_типа_продукции;

    -- Добавить информацию об уникальном названии типа продукции
    insert into "Название_продукции" (ид_типа, название)
    values (var_ид_типа_продукции, arg_название);

    -- Добавить информацию об уникальном описании типа продукции
    insert into "Описание_продукции" (ид_типа, описание)
    values (var_ид_типа_продукции, arg_описание);

    return var_ид_типа_продукции;
end;
$$ language plpgsql;


-- Функция создания экземпляра склада готовой продукции
create or replace function f_i_Склад_готовой_продукции(
    arg_ид_завода int,
    arg_адрес text
)
returns int as $$
declare
    var_ид_склада int;
begin
    insert into "Склад_готовой_продукции" (ид_завода)
    values (arg_ид_завода)
    returning ид into var_ид_склада;

    -- Добавить информацию об уникальном адресе склада
    insert into "Адрес_склада_готовой_продукции" (ид_склада, адрес)
    values (var_ид_склада, arg_адрес);

    return var_ид_склада;
end;
$$ language plpgsql;


-- Функция создания экземпляра типа материала
create or replace function f_i_Тип_материала(
    arg_цена real,
    arg_название text,
    arg_описание text
)
returns int as $$
declare
    var_ид_типа_материала int;
begin
    insert into "Тип_материала" (цена)
    values (arg_цена)
    returning ид into var_ид_типа_материала;

    -- Добавить информацию об уникальном названии типа продукции
    insert into "Название_материала" (ид_типа, название)
    values (var_ид_типа_материала, arg_название);

    -- Добавить информацию об уникальном описании типа продукции
    insert into "Описание_материала" (ид_типа, описание)
    values (var_ид_типа_материала, arg_описание);

    return var_ид_типа_материала;
end;
$$ language plpgsql;


-- Функция создания экземпляра склада сырья
create or replace function f_i_Склад_сырья(
    arg_ид_завода int,
    arg_адрес text
)
returns int as $$
declare
    var_ид_склада int;
begin
    insert into "Склад_сырья" (ид_завода)
    values (arg_ид_завода)
    returning ид into var_ид_склада;

    -- Добавить информацию об уникальном адресе склада
    insert into "Адрес_склада_сырья" (ид_склада, адрес)
    values (var_ид_склада, arg_адрес);

    return var_ид_склада;
end;
$$ language plpgsql;


-- Функция создания экземпляра склада сырья
create or replace function f_i_Завод(
    arg_название text,
    arg_номер_телефона varchar[20],
    arg_email varchar[64],
    arg_адрес text
)
returns int as $$
declare
    var_ид_завода int;
begin
    insert into "Завод" (название, номер_телефона, email)
    values (arg_название, arg_номер_телефона, arg_email)
    returning ид into var_ид_завода;

    -- Добавить информацию об уникальном адресе завода
    insert into "Адрес_завода" (ид_завода, адрес)
    values (var_ид_завода, arg_адрес);

    return var_ид_завода;
end;
$$ language plpgsql;


/*
    +------------------------------+
    | Функции для бизнес-процессов |
    +------------------------------+
*/

/*
    1. Регистрация клиентов:
        1.1. Клиенты регистрируются в системе, предоставляя контактные данные и информацию о компании.
        1.2. Клиент получает доступ к личному кабинету.
*/

-- 1.1. Регистрация <=> f_i_Клиент

-- 1.2. Доступ
-- Идентификация и аутентификация
create or replace function f_аутентифицировать (
    arg_название "Доступные_организации_enum",
    arg_пароль varchar[64]
)
returns bool as $$
declare
    var_result bool;
begin
    var_result = false;

    select пароль = arg_пароль into var_result
    from "Клиент" inner join "Организация_клиента"
    on "Клиент".ид = "Организация_клиента".ид_клиента
    where название = arg_название;

    return var_result;
end;
$$ language plpgsql;


-- drop function if exists f_g_Клиент(arg_название "Доступные_организации_enum", arg_пароль varchar[]);
-- Получить данные клиента
create or replace function f_g_Клиент (
    arg_название "Доступные_организации_enum",
    arg_пароль varchar[64]
)
returns table (
    ид int,
    номер_телефона varchar[20],
    email varchar[64],
    пароль varchar[64]
)
as $$
begin
    return query
        select ид, номер_телефона, email, название
        from "Клиент" inner join "Организация_клиента"
        on "Клиент".ид = "Организация_клиента".ид_клиента
        where название = arg_название and пароль = arg_пароль;
end;
$$ language plpgsql;


create or replace function f_g_все_названия_организаций ()
returns table(
    название text
)
as $$
begin
    return query
        select название from "Организация_клиента";
end;
$$ language plpgsql;

/*
    2. Управление информацией о производимой продукции и необходимом сырье (типы):
        2.1. Отслеживание,
        2.2. Добавление,
        2.3. Обновление,
        2.4. Удаление информации о продукции/сырье в базе данных.
*/

-- 2.1. Отслеживание
-- Получить все типы продукции
create or replace function f_g_все_типы_продукции()
returns table (
    ид int,
    название text,
    описание text,
    цена real
)
as $$
begin
    return query
        select ид, название, описание, цена
        from "Тип_продукции" inner join "Название_продукции"
            on "Тип_продукции".ид = "Название_продукции".ид_типа
        inner join "Описание_продукции"
            on "Тип_продукции".ид = "Описание_продукции".ид_типа;
end;
$$ language plpgsql;

-- 2.2. Добавление
-- Добавить новый тип продукции <=> f_i_Тип_продукции
-- Добавить новый тип сырья <=> f_i_Тип_материала


-- 2.3. Обновление
-- Обновить название продукции
create or replace procedure p_u_название_продукции(
    arg_ид_типа int,
    arg_название text
)
as $$
begin
    update "Название_продукции" set название = arg_название
    where ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

-- Обновить название материала
create or replace procedure p_u_название_материала(
    arg_ид_типа int,
    arg_название text
)
as $$
begin
    update "Название_материала" set название = arg_название
    where ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

-- Обновить описание продукции
create or replace procedure p_u_описание_продукции(
    arg_ид_типа int,
    arg_описание text
)
as $$
begin
    update "Описание_продукции" set описание = arg_описание
    where ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

-- Обновить описание материала
create or replace procedure p_u_описание_материала(
    arg_ид_типа int,
    arg_описание text
)
as $$
begin
    update "Описание_материала" set описание = arg_описание
    where ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

-- Обновить цену продукции
create or replace procedure p_u_цену_продукции(
    arg_ид_типа int,
    arg_цена real
)
as $$
begin
    update "Тип_продукции" set цена = arg_цена
    where ид = arg_ид_типа;
end;
$$ language plpgsql;

-- Обновить цену материала
create or replace procedure p_u_цену_материала(
    arg_ид_типа int,
    arg_цена real
)
as $$
begin
    update "Тип_материала" set цена = arg_цена
    where ид = arg_ид_типа;
end;
$$ language plpgsql;

/*
    3. Управление заказами:
        3.1. Отслеживание,
        3.2. Принятие,
        3.3. Обновление заказов клиентов.
*/

-- 3.1. Отслеживание
-- Получить все заказы клиента
create or replace function f_g_все_заказы_клиента (
    arg_ид_клиента int
)
returns table (
    ид int,
    ид_консультанта int,
    статус "Статус_заказа_enum",
    поступил timestamp,
    завершен timestamp,
    итоговая_сумма real
)
as $$
begin
    return query
        select ид, ид_консультанта, статус, поступил, завершен, итоговая_сумма
        from "Заказ"
        where ид_клиента = arg_ид_клиента;
end;
$$ language plpgsql;


-- 3.2. Принятие
-- Создать новый заказ
create or replace function f_ig_новый_заказ (
    arg_ид_клиента int
)
returns record as $$
declare
    var_заказ record;
    var_ид_консультанта int;
begin
    select ид into var_ид_консультанта
    from "Консультант" order by random() limit 1;

    insert into "Заказ" (ид_клиента, ид_консультанта, статус, поступил, завершен, итоговая_сумма)
    values (arg_ид_клиента, var_ид_консультанта, 'формируется', now(), null, 0)
    returning * into var_заказ;

    return var_заказ;
end;
$$ language plpgsql;


-- 3.3. Обновление
-- Подтверждение
create or replace procedure p_u_подтвердить_заказ (
    arg_ид_заказа int
)
as $$
begin
    update "Заказ" set статус = 'ожидает оплаты'
    where ид = arg_ид_заказа;
end;
$$ language plpgsql;

-- Оплата
create or replace procedure p_u_оплата_заказа (
    arg_ид_заказа int
)
as $$
begin
    update "Заказ" set статус = 'выполняется'
    where ид = arg_ид_заказа;
end;
$$ language plpgsql;

-- Успешное завершение
create or replace procedure p_u_заказ_выполнен (
    arg_ид_заказа int
)
as $$
begin
    update "Заказ" set статус = 'выполнен'
    where ид = arg_ид_заказа;
end;
$$ language plpgsql;

-- Отклонен
create or replace procedure p_u_заказ_отклонен (
    arg_ид_заказа int
)
as $$
begin
    update "Заказ" set статус = 'отклонен'
    where ид = arg_ид_заказа;
end;
$$ language plpgsql;


create or replace procedure p_i_добавить_продукцию_к_заказу (
    arg_ид_заказа int,
    arg_ид_типа int
)
as $$
begin
    insert into "Продукция_в_заказе" (ид_заказа, ид_типа, количество)
    values (arg_ид_заказа, arg_ид_типа, 1);
end;
$$ language plpgsql;

create or replace procedure p_u_изменить_количество_продукции (
    arg_ид_заказа int,
    arg_ид_типа int,
    arg_количество int
)
as $$
begin
    update "Продукция_в_заказе" set количество = arg_количество
    where ид_заказа = arg_ид_заказа and ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

create or replace procedure p_d_убрать_продукцию_из_заказа (
    arg_ид_заказа int,
    arg_ид_типа int
)
as $$
begin
    delete from "Продукция_в_заказе"
    where ид_заказа = arg_ид_заказа and ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

/*
    4. Управление складами (количество и типы):
        4.1. Отслеживание,
        4.2. Перемещение продукции и материалов между складами
        4.3. Сборка и возвращение продукции
*/

-- 4.1. Отслеживание
-- Получить информацию по всем продуктам на складе
create or replace function f_g_готовая_продукция_на_складе(
    arg_ид_склада int
)
returns table(
    ид_типа int,
    название text,
    описание text,
    цена real,
    количество int
)
as $$
begin
    return query
        select ид, название, описание, цена, количество from
        "Готовая_продукция" inner join "Тип_продукции"
            on "Тип_продукции".ид = "Готовая_продукция".ид_типа
        inner join "Название_продукции"
            on "Название_продукции".ид_типа = "Готовая_продукция".ид_типа
        inner join "Описание_продукции"
            on "Описание_продукции".ид_типа = "Готовая_продукция".ид_типа
        where ид_склада = arg_ид_склада;
end;
$$ language plpgsql;

-- Получить информацию по всем материалам на складе
create or replace function f_g_материалы_на_складе(
    arg_ид_склада int
)
returns table(
    ид_типа int,
    название text,
    описание text,
    цена real,
    количество int
)
as $$
begin
    return query
        select ид, название, описание, цена, количество from
            "Материалы" inner join "Тип_материала"
                on "Тип_материала".ид = "Материалы".ид_типа
            inner join "Название_материала"
                on "Название_материала".ид_типа = "Материалы".ид_типа
            inner join "Описание_материала"
                on "Описание_материала".ид_типа = "Материалы".ид_типа
        where ид_склада = arg_ид_склада;
end;
$$ language plpgsql;

-- 4.2. Перемещение продукции и материалов
-- Принести продукцию на склад
create or replace procedure p_u_принести_продукцию_на_склад(
    arg_ид_склада int,
    arg_ид_типа int,
    arg_количество int
)
as $$
begin
    update "Готовая_продукция" set количество = количество + arg_количество
    where ид_склада = arg_ид_склада and ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

-- Принести материалы на склад
create or replace procedure p_u_принести_материалы_на_склад(
    arg_ид_склада int,
    arg_ид_типа int,
    arg_количество int
)
as $$
begin
    update "Материалы" set количество = количество + arg_количество
    where ид_склада = arg_ид_склада and ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

-- Вынести продукцию со склада
create or replace procedure p_u_вынести_продукцию_со_склада(
    arg_ид_склада int,
    arg_ид_типа int,
    arg_количество int
)
as $$
begin
    update "Готовая_продукция" set количество = количество - arg_количество
    where ид_склада = arg_ид_склада and ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

-- Вынести материалы со склада
create or replace procedure p_u_вынести_материалы_со_склада(
    arg_ид_склада int,
    arg_ид_типа int,
    arg_количество int
)
as $$
begin
    update "Готовая_продукция" set количество = количество - arg_количество
    where ид_склада = arg_ид_склада and ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

-- Переместить продукцию с одного склада на другой
create or replace procedure p_u_переместить_продукцию_на_склад(
    arg_ид_склада1 int,
    arg_ид_типа int,
    arg_количество int,
    arg_ид_склада2 int
)
as $$
begin
    begin
        execute p_u_вынести_продукцию_со_склада(arg_ид_склада1, arg_ид_типа, arg_количество);
        execute p_u_принести_продукцию_на_склад(arg_ид_склада2, arg_ид_типа, arg_количество);
    exception when others
    then
        raise notice 'неудачное перемещение готовой продукции: %; %', SQLSTATE, SQLERRM;
    end;
end;
$$ language plpgsql;

-- Переместить материалы с одного склада на другой
create or replace procedure p_u_переместить_материалы_на_склад(
    arg_ид_склада1 int,
    arg_ид_типа int,
    arg_количество int,
    arg_ид_склада2 int
)
as $$
begin
    begin
        execute p_u_вынести_материалы_со_склада(arg_ид_склада1, arg_ид_типа, arg_количество);
        execute p_u_принести_материалы_на_склад(arg_ид_склада2, arg_ид_типа, arg_количество);
    exception when others
    then
        raise notice 'неудачное перемещение материала: %; %', SQLSTATE, SQLERRM;
    end;
end;
$$ language plpgsql;

-- 4.3. Сборка и возвращение продукции
-- Сделать вид что произвели продукцию
create or replace procedure p_u_произвести_продукцию_под_заказ (
    arg_ид_заказа int,
    arg_ид_типа int,
    arg_ид_склада int
)
as $$
begin
    execute p_u_принести_продукцию_на_склад(arg_ид_склада, arg_ид_типа, (select количество from "Продукция_в_заказе" where ид_заказа = arg_ид_заказа and ид_типа = arg_ид_типа));

    update "Продукция_в_заказе" set статус = 'ожидает сборки'
    where ид_заказа = arg_ид_заказа and ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

-- Собрать необходимое количество со всех складов
create or replace function f_u_собрать_продукцию_под_заказ(
    arg_ид_заказа int,
    arg_ид_типа int
)
returns bool as $$
declare
    var_row record;
    var_осталось_собрать int;
    var_вынести_со_склада int;
begin
    select количество
    from "Продукция_в_заказе"
    where ид_заказа = arg_ид_заказа and ид_типа = arg_ид_типа
    into var_осталось_собрать;

    if (select sum(количество) from "Готовая_продукция" where ид_типа = arg_ид_типа) >= var_осталось_собрать then
        for var_row in select * from "Готовая_продукция" where ид_типа = arg_ид_типа and количество > 0 loop
            if var_осталось_собрать > var_row.количество then
                var_вынести_со_склада = var_row.количество;
            else
                var_вынести_со_склада = var_осталось_собрать;
            end if;

            execute p_u_вынести_продукцию_со_склада(var_row.ид_склада, arg_ид_типа, var_вынести_со_склада);
            var_осталось_собрать = var_осталось_собрать - var_вынести_со_склада;
            if var_осталось_собрать == 0 then
                exit;
            end if;
        end loop;

        update "Продукция_в_заказе" set статус = 'собрана под заказ'
        where ид_заказа = arg_ид_заказа and ид_типа = arg_ид_типа;

        return true;
    end if;

    return false;
end;
$$ language plpgsql;

create or replace procedure p_u_вернуть_продукцию_на_склады(
    arg_ид_заказа int,
    arg_ид_типа int
)
as $$
declare
    var_row record;
    var_осталось_вернуть int;
    var_вернуть_на_склад int;
    var_равное_количество int;
begin
    select количество
    from "Продукция_в_заказе"
    where ид_заказа = arg_ид_заказа and ид_типа = arg_ид_типа
    into var_осталось_вернуть;

    var_равное_количество = (var_осталось_вернуть / (select count(*) from "Склад_готовой_продукции"))::int;

    for var_row in select * from "Готовая_продукция" where ид_типа = arg_ид_типа loop
        if var_осталось_вернуть > var_равное_количество and var_осталось_вернуть < 2 * var_равное_количество then
            var_вернуть_на_склад = var_осталось_вернуть;
        else
            var_вернуть_на_склад = var_равное_количество;
        end if;

        execute p_u_принести_продукцию_на_склад(var_row.ид_склада, arg_ид_типа, var_вернуть_на_склад);
        var_осталось_вернуть = var_осталось_вернуть - var_вернуть_на_склад;
        if var_осталось_вернуть == 0 then
            exit;
        end if;
    end loop;

    update "Продукция_в_заказе" set статус = 'возвращена'
    where ид_заказа = arg_ид_заказа and ид_типа = arg_ид_типа;
end;
$$ language plpgsql;

create or replace procedure p_u_состояние_продукции_в_заказе(
    arg_ид_заказа int
)
as $$
declare
    var_row record;
begin
    for var_row in select * from "Продукция_в_заказе" where ид_заказа = arg_ид_заказа loop
        if var_row.статус == 'ожидает производства' then
            execute p_u_произвести_продукцию_под_заказ(var_row.ид_заказа, var_row.ид_типа, var_row.количество);
        elseif var_row.статус == 'ожидает сборки' then
            execute f_u_собрать_продукцию_под_заказ(var_row.ид_заказа, var_row.ид_типа);
        elseif var_row.статус == 'ожидает возвращения' then
            execute p_u_вернуть_продукцию_на_склады(var_row.ид_заказа, var_row.ид_типа);
        end if;
    end loop;
end;
$$ language plpgsql;

/*
    5. Управление персоналом:
        5.1. Отслеживание,
        5.2. Добавление,
        5.3. Изменение,
        5.4. Удаление расписаний и сотрудников/консультантов
*/

-- 5.1. Отслеживание
-- Получить расписания консультантов
create or replace function f_g_Расписание_консультантов()
returns table (
    ид int,
    рабочее_время int,
    описание text
)
as $$
begin
    return query
        select * from "Расписание_консультантов";
end;
$$ language plpgsql;

-- Получить расписания сотрудников
create or replace function f_g_Расписание_сотрудников()
returns table (
    ид int,
    рабочее_время int,
    описание text
)
as $$
begin
    return query
        select * from "Расписание_сотрудников";
end;
$$ language plpgsql;

-- Получить консультантов в службе поддержки
create or replace function f_g_Консультант(
    arg_ид_службы_поддержки int
)
returns table (
    ид int,
    ид_службы_поддержки int,
    ид_расписания int,
    ФИО varchar[64],
    номер_телефона varchar[20],
    email varchar[64],
    пароль varchar[64]
)
as $$
begin
    return query
        select * from "Консультант"
        where ид_службы_поддержки = arg_ид_службы_поддержки;
end;
$$ language plpgsql;

-- Получить сотрудников в цехе
create or replace function f_g_Сотрудник(
    arg_ид_цеха int
)
returns table (
    ид int,
    ид_цеха int,
    ид_расписания int,
    ФИО varchar[64],
    должность "Должность_enum",
    номер_телефона varchar[20],
    email varchar[64],
    пароль varchar[64]
)
as $$
begin
    return query
        select * from "Сотрудник"
        where ид_цеха = arg_ид_цеха;
end;
$$ language plpgsql;

-- 5.2. Добавление
-- Добавить расписание консультантов
create or replace function f_i_Расписание_консультантов(
    arg_рабочее_время int,
    arg_описание text
)
returns int as $$
declare
    var_ид_расписания int;
begin
    insert into "Расписание_консультантов" (рабочее_время, описание)
    values (arg_рабочее_время, arg_описание)
    returning ид into var_ид_расписания;

    return var_ид_расписания;
end;
$$ language plpgsql;

-- Добавить расписание сотрудников
create or replace function f_i_Расписание_сотрудников(
    arg_рабочее_время int,
    arg_описание text
)
returns int as $$
declare
    var_ид_расписания int;
begin
    insert into "Расписание_сотрудников" (рабочее_время, описание)
    values (arg_рабочее_время, arg_описание)
    returning ид into var_ид_расписания;

    return var_ид_расписания;
end;
$$ language plpgsql;

-- Добавить консультанта
create or replace function f_i_Консультант(
    arg_ид_службы_поддержки int,
    arg_ид_расписания int,
    arg_ФИО varchar[64],
    arg_номер_телефона varchar[20],
    arg_email varchar[64],
    arg_пароль varchar[64]
)
returns int as $$
declare
    var_ид_консультанта int;
begin
    insert into "Консультант" (ид_службы_поддержки, ид_расписания, "ФИО", номер_телефона, email, пароль)
    values (arg_ид_службы_поддержки, arg_ид_расписания, arg_ФИО, arg_номер_телефона, arg_email, arg_пароль)
    returning ид into var_ид_консультанта;

    return var_ид_консультанта;
end;
$$ language plpgsql;

-- Добавить сотрудника
create or replace function f_i_Сотрудник(
    arg_ид_цеха int,
    arg_ид_расписания int,
    arg_ФИО varchar[64],
    arg_должность "Должность_enum",
    arg_номер_телефона varchar[20],
    arg_email varchar[64],
    arg_пароль varchar[64]
)
returns int as $$
declare
    var_ид_сотрудника int;
begin
    insert into "Сотрудник" (ид_цеха, ид_расписания, "ФИО", должность, номер_телефона, email, пароль)
    values (arg_ид_цеха, arg_ид_расписания, arg_ФИО, arg_должность, arg_номер_телефона, arg_email, arg_пароль)
    returning ид into var_ид_сотрудника;

    return var_ид_сотрудника;
end;
$$ language plpgsql;

-- 5.3. Изменение
-- Измененить расписание консультантов
create or replace procedure p_u_Расписание_консультантов(
    arg_ид_расписания int,
    arg_рабочее_время int,
    arg_описание text
)
as $$
begin
    update "Расписание_консультантов" set рабочее_время = arg_рабочее_время, описание = arg_описание
    where ид = arg_ид_расписания;
end;
$$ language plpgsql;

-- Измененить расписание сотрудников
create or replace procedure p_u_Расписание_сотрудников(
    arg_ид_сотрудника int,
    arg_рабочее_время int,
    arg_описание text
)
as $$
begin
    update "Расписание_сотрудников" set рабочее_время = arg_рабочее_время, описание = arg_описание
    where ид = arg_ид_сотрудника;
end;
$$ language plpgsql;

-- Изменить консультанта
create or replace procedure p_u_Консультант(
    arg_ид_консультанта int,
    arg_ид_службы_поддержки int,
    arg_ид_расписания int,
    arg_ФИО varchar[64],
    arg_номер_телефона varchar[20],
    arg_email varchar[64],
    arg_пароль varchar[64]
)
as $$
begin
    update "Консультант"
    set ид_службы_поддержки = arg_ид_службы_поддержки,
        ид_расписания = arg_ид_расписания,
        "ФИО" = arg_ФИО,
        номер_телефона = arg_номер_телефона,
        email = arg_email,
        пароль = arg_пароль
    where ид = arg_ид_консультанта;
end;
$$ language plpgsql;

-- Изменить сотрудника
create or replace procedure p_u_Сотрудник(
    arg_ид_сотрудника int,
    arg_ид_цеха int,
    arg_ид_расписания int,
    arg_ФИО varchar[64],
    arg_должность "Должность_enum",
    arg_номер_телефона varchar[20],
    arg_email varchar[64],
    arg_пароль varchar[64]
)
as $$
begin
    update "Сотрудник"
    set ид_цеха = arg_ид_цеха,
        ид_расписания = arg_ид_расписания,
        "ФИО" = arg_ФИО,
        должность = arg_должность,
        номер_телефона = arg_номер_телефона,
        email = arg_email,
        пароль = arg_пароль
    where ид = arg_ид_сотрудника;
end;
$$ language plpgsql;

-- 5.4. Удаление
-- Удалить расписание консультантов
create or replace procedure p_d_Расписание_консультантов(
    arg_ид_расписания int
)
as $$
begin
    delete from "Расписание_консультантов"
    where ид = arg_ид_расписания;
end;
$$ language plpgsql;

-- Удалить расписание сотрудников
create or replace procedure p_d_Расписание_сотрудников(
    arg_ид_расписания int
)
as $$
begin
    delete from "Расписание_сотрудников"
    where ид = arg_ид_расписания;
end;
$$ language plpgsql;

-- Удалить консультанта
create or replace procedure p_d_Консультант(
    arg_ид_консультанта int
)
as $$
begin
    delete from "Консультант"
    where ид = arg_ид_консультанта;
end;
$$ language plpgsql;

-- Удалить сотрудника
create or replace procedure p_d_Сотрудник(
    arg_ид_сотрудника int
)
as $$
begin
    delete from "Сотрудник"
    where ид = arg_ид_сотрудника;
end;
$$ language plpgsql;

/*
    6. Управление состоянием оборудования:
        6.1. Отслеживание,
        6.2. Добавить оборудование
        6.3. Изменить состояние оборудования
        6.4. Удалить оборудование
*/

-- 6.1. Отслеживание
-- Получить всё информацию об оборудовании в цехе
create or replace function f_g_все_оборудование (
    arg_ид_цеха int
)
returns table (
    ид int,
    ид_цеха int,
    состояние "Состояние_оборудования_enum",
    название text,
    описание text,
    дата_выпуска date
)
as $$
begin
    return query
        select * from "Оборудование"
        where ид_цеха = arg_ид_цеха;
end;
$$ language plpgsql;

-- 6.2. Добавить
create or replace procedure p_i_Оборудование (
    arg_ид_цеха int,
    arg_состояние "Состояние_оборудования_enum",
    arg_название text,
    arg_описание text,
    arg_дата_выпуска date
)
as $$
begin
    insert into "Оборудование" (ид_цеха, состояние, название, описание, дата_выпуска)
    values (arg_ид_цеха, arg_состояние, arg_название, arg_описание, arg_дата_выпуска);
end;
$$ language plpgsql;

-- 6.3. Изменить состояние
-- Изменить состояние оборудования
create or replace procedure p_u_состояние_оборудования (
    arg_ид_оборудования int,
    arg_состояние "Состояние_оборудования_enum"
)
as $$
begin
    update "Оборудование" set состояние = arg_состояние
    where ид = arg_ид_оборудования;
end;
$$ language plpgsql;

-- 6.4. Удалить
create or replace procedure p_d_Оборудование (
    arg_ид_оборудования int
)
as $$
begin
    delete from "Оборудование"
    where ид = arg_ид_оборудования;
end;
$$ language plpgsql;

/*
    7. Коммуникация с клиентом:
        7.1. Чтение,
        7.2. Отправка сообщений между клиентом и консультантом
*/

-- 7.1. Чтение
-- Получить данные чата
create or replace function f_g_все_сообщения (
    arg_ид_заказа int
)
returns table (
    ид int,
    отправитель "Отправитель_enum",
    текст text,
    дата_время timestamp
)
as $$
begin
    return query
        select ид, отправитель, текст, дата_время
        from "Сообщение"
        where ид_заказа = arg_ид_заказа;
end;
$$ language plpgsql;


-- 7.2. Отправка
-- Сообщение от клиента
create or replace procedure p_i_сообщение_клиента (
    arg_ид_заказа int,
    arg_текст text,
    arg_дата_время timestamp
)
as $$
begin
    insert into "Сообщение" (ид_заказа, отправитель, текст, дата_время)
    values (arg_ид_заказа, 'клиент', arg_текст, arg_дата_время);
end;
$$ language plpgsql;

-- Сообщение от консультанта
create or replace procedure p_i_сообщение_консультанта (
    arg_ид_заказа int,
    arg_текст text,
    arg_дата_время timestamp
)
as $$
begin
    insert into "Сообщение" (ид_заказа, отправитель, текст, дата_время)
    values (arg_ид_заказа, 'консультант', arg_текст, arg_дата_время);
end;
$$ language plpgsql;

/*
    8. Сотрудничество:
        8.1. Получить информацию по сотрудничествам
        8.2. Отправить заявку на сотрудничество
        8.3. Образовать,
        8.4. Прекратить,
        8.5. Отклонить сотрудничество
*/

-- 8.1. Получить информацию по сотрудничествам
-- Доступ только у завода
create or replace function f_g_все_сотрудничества_завода (
    arg_ид_завода int
)
returns table(
    ид_завода int,
    ид_организации int,
    форма "Форма_сотрудничества_enum",
    состояние "Состояние_сотрудничества_enum",
    поступило timestamp,
    завершено timestamp
)
as $$
begin
    return query
        select ид_завода, ид_организации, форма, состояние, поступило, завершено
        from "Сотрудничество"
        where ид_завода = arg_ид_завода;

end;
$$ language plpgsql;

-- 8.2. Отправить заявку
-- Доступ только у организаций
create or replace procedure f_ug_заявка_на_сотрудничество (
    arg_ид_завода int,
    arg_ид_организации int
)
as $$
begin
    update "Сотрудничество" set состояние = 'на рассмотрении'
    where ид_завода = arg_ид_завода and ид_организации = arg_ид_организации;
end;
$$ language plpgsql;

-- 8.3. Образовать сотрудничество
-- Доступ только у завода
create or replace procedure p_u_принять_сотрудничество (
    arg_ид_завода int,
    arg_ид_организации int
)
as $$
begin
    update "Сотрудничество" set состояние = 'в силе'
    where ид_завода = arg_ид_завода and ид_организации = arg_ид_организации;
end;
$$ language plpgsql;

-- 8.4. Прекратить сотрудничество
-- Доступ только у завода
create or replace procedure p_u_прекратить_сотрудничество (
    arg_ид_завода int,
    arg_ид_организации int
)
as $$
begin
    update "Сотрудничество" set состояние = 'прекращено', завершено = now()
    where ид_завода = arg_ид_завода and ид_организации = arg_ид_организации;
end;
$$ language plpgsql;

-- 8.5. Отклонить сотрудничество
-- Доступ только у завода
create or replace procedure p_u_отклонить_сотрудничество (
    arg_ид_завода int,
    arg_ид_организации int
)
as $$
begin
    update "Сотрудничество" set состояние = 'отклонено', завершено = now()
    where ид_завода = arg_ид_завода and ид_организации = arg_ид_организации;
end;
$$ language plpgsql;

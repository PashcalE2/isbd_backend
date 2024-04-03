/*
Обозначения:
	- Функция для триггера:
		(1) p <=> procedure
		(1) f <=> function
		(1) tf <=> trigger function
		(2) b <=> before
		(2) a <=> after
		(3) i <=> insert
		(3) u <=> update
		(3) d <=> delete
		- пример: trigger_function_before_insert_Цех <=> tf_b_i_Цех
*/

-- 1.1. Проверка контактов для сотрудников поддержки клиентов

create or replace function f_проверить_контакты(
    номер_телефона text,
    email text
)
returns bool as $$
begin
	return номер_телефона ~ E'^\\+?\\d{1,19}$' and email ~ E'^(\\w+\\.)*\\w+@\\w+\\.\\w+$';
end
$$ language plpgsql;

create or replace function tf_b_i_Консультант()
returns trigger as $$
begin
    -- Проверка телефона и почты
    if not f_проверить_контакты(new.номер_телефона, new.email) then
        -- Замена на контактные данные организации
        select "Служба_поддержки".номер_телефона, "Служба_поддержки".email into new.номер_телефона, new.email
        from "Служба_поддержки"
        where ид = new.ид_службы_поддержки;
    end if;
	
	return new;
end;
$$ language plpgsql;


create or replace trigger b_i_Консультант
before insert on "Консультант"
for each row
execute function tf_b_i_Консультант();

-- 1.2. Проверка контактов для сотрудников цехов

create or replace function tf_b_i_Сотрудник()
returns trigger as $$
begin
    -- Проверка телефона и почты
    if not f_проверить_контакты(new.номер_телефона, new.email) then
        -- Замена на контактные данные организации
        select "Завод".номер_телефона, "Завод".email into new.номер_телефона, new.email
        from "Цех" inner join "Завод" on "Цех".ид_завода = "Завод".ид
        where "Цех".ид = new.ид_цеха;
    end if;
	
	return new;
end;
$$ language plpgsql;


create or replace trigger b_i_Сотрудник
before insert on "Сотрудник"
for each row
execute function tf_b_i_Сотрудник();

-- 1.3. Проверка контактов для заводов, клиентов и организаций

create or replace function tf_b_i_контакты_представителя()
returns trigger as $$
begin
    -- Проверка телефона и почты
    if not f_проверить_контакты(new.номер_телефона, new.email) then
        raise exception '"%", "%" - плохие контакты', new.номер_телефона, new.email;
    end if;
	
	return new;
end;
$$ language plpgsql;


create or replace trigger b_i_Клиент
before insert on "Клиент"
for each row
execute function tf_b_i_контакты_представителя();

create or replace trigger b_i_Организация
before insert on "Организация"
for each row
execute function tf_b_i_контакты_представителя();

create or replace trigger b_i_Завод
before insert on "Завод"
for each row
execute function tf_b_i_контакты_представителя();

-- 2.1. / 3. Проверка изменения заказа

create or replace function tf_b_u_Заказ()
returns trigger as $$
begin
    if old.статус = 'отклонен' or old.статус = 'выполнен' then
        raise exception 'отклоненные или выполненные заказы модифицировать нельзя';
    end if;

    -- Проверка дат
	if new.завершен is not null then
		if not (new.статус = 'отклонен' or new.статус = 'выполнен') then
			raise exception 'менять дату завершения заказа можно только если он выполнен/отклонен';
		end if;
		
		if new.завершен < new.поступил then
			raise exception 'дата завершения заказа (%) раньше даты поступления заказа (%)', new.завершен, new.поступил;
		end if;
	end if;
    return new;
end;
$$ language plpgsql;


create or replace trigger b_u_Заказ
before update on "Заказ"
for each row
execute function tf_b_u_Заказ();

create or replace function tf_a_u_Заказ()
    returns trigger as $$
begin
    if old.статус = 'отклонен' then
        update "Продукция_в_заказе"
        set статус = 'ожидает возвращения'
        where "Продукция_в_заказе".ид_заказа = new.ид;
    end if;

    return old;
end;
$$ language plpgsql;


create or replace trigger a_u_Заказ
    after update on "Заказ"
    for each row
execute function tf_a_u_Заказ();

-- 2.2. Проверка изменения сотрудничества

create or replace function tf_b_u_Сотрудничество()
returns trigger as $$
begin
    if old.состояние = 'прекращено' or old.состояние = 'отклонено' then
        raise exception 'прекращенные или отклоненные сотрудничества модифицировать нельзя';
    end if;

    if new.состояние = 'в силе' and old.состояние != 'на рассмотрении' then
        raise exception 'принимать можно только сотрудничества на рассмотрении';
    end if;

    if new.состояние = 'отклонено' and old.состояние != 'на рассмотрении' then
        raise exception 'отклонять можно только сотрудничества на рассмотрении';
    end if;

    if new.состояние = 'прекращено' and old.состояние != 'в силе' then
        raise exception 'прекращать можно только сотрудничества в силе';
    end if;

	if new.завершено is not null then
		if not (new.состояние = 'прекращено' or new.состояние = 'отклонено') then
			raise exception 'менять дату прекращения/отклонения сотрудничества можно только если оно прекращено/отклонено';
		end if;
		
		if new.завершено < new.поступило then
			raise exception 'дата окончания сотрудничества (%) раньше даты начала сотрудничества (%)', new.завершено, new.поступило;
		end if;
	end if;

    return new;
end;
$$ language plpgsql;


create or replace trigger b_u_Сотрудничество
before update on "Сотрудничество"
for each row
execute function tf_b_u_Сотрудничество();

-- 4.1. / 4.2. Проверка вставки/изменения расписания

create or replace function проверить_рабочее_время(
    рабочее_время int,
    максимальное int
)
returns bool as $$
begin
	return рабочее_время <= максимальное;
end
$$ language plpgsql;

create or replace function tf_b_iu_часы_расписания()
returns trigger as $$
begin
    if not проверить_рабочее_время(new.рабочее_время, 60) then
		raise exception 'Рабочие часы в рабочую неделю (5 суток) не могут превышать % часов', 60;
	end if;
	
    return new;
end;
$$ language plpgsql;


create or replace trigger b_i_or_update_Расписание_консультантов
before insert or update on "Расписание_консультантов"
for each row
execute function tf_b_iu_часы_расписания();

create or replace trigger b_i_or_update_Расписание_сотрудников
before insert or update on "Расписание_сотрудников"
for each row
execute function tf_b_iu_часы_расписания();

-- 5.1. Проверка удаления расписания консультантов

create or replace function tf_b_d_Расписание_консультантов()
returns trigger as $$
declare
	var_консультанты int;
begin
	var_консультанты = (select count(*) from
		"Консультант" inner join "Расписание_консультантов"
		on "Консультант".ид_расписания = "Расписание_консультантов".ид
		where "Расписание_консультантов".ид = old.ид);
		
    if var_консультанты > 0 then
		raise exception 'Нельзя удалять расписание консультантов по которому кто-то работает (% консультантов)', var_консультанты;
	end if;
	
	return old;
end;
$$ language plpgsql;


create or replace trigger b_d_Расписание_сотрудников
before delete on "Расписание_консультантов"
for each row
execute function tf_b_d_Расписание_консультантов();

-- 5.2. Проверка удаления расписания сотрудников

create or replace function tf_b_d_Расписание_сотрудников()
returns trigger as $$
declare
	var_сотрудники int;
begin
    var_сотрудники = (select count(*)
		from "Сотрудник" inner join "Расписание_сотрудников"
        on "Сотрудник".ид_расписания = "Расписание_сотрудников".ид
        where "Сотрудник".ид_расписания = old.ид);
        
    if var_сотрудники > 0 then
        raise exception 'Нельзя удалять расписание сотрудников по которому кто-то работает (% сотрудников)', var_сотрудники;
    end if;
    
    return old;
end
$$ language plpgsql;


create or replace trigger b_d_Расписание_сотрудников
before delete on "Расписание_сотрудников"
for each row
execute function tf_b_d_Расписание_сотрудников();

-- 6. Изменения заказа при вставке/изменении/удалении продукции в заказе

drop procedure if exists p_подсчет_суммы_заказа;
create or replace procedure p_подсчет_суммы_заказа(
    arg_ид_заказа int
)
as $$
begin
    if (select count(*) from "Продукция_в_заказе" where "Продукция_в_заказе".ид_заказа = arg_ид_заказа) = 0 then
        update "Заказ" set итоговая_сумма = 0
            where "Заказ".ид = arg_ид_заказа;
    else
        update "Заказ" set итоговая_сумма =
            (select sum(("Продукция_в_заказе".количество)::real * "Тип_продукции".цена)
                from "Продукция_в_заказе" inner join "Тип_продукции"
                on "Продукция_в_заказе".ид_типа = "Тип_продукции".ид
                where "Продукция_в_заказе".ид_заказа = arg_ид_заказа)
            where "Заказ".ид = arg_ид_заказа;
    end if;
end
$$ language plpgsql;

drop function if exists tf_b_iu_Продукция_в_заказе;
create or replace function tf_a_iu_Продукция_в_заказе()
returns trigger as $$
begin
    call p_подсчет_суммы_заказа(new.ид_заказа);
    return new;
end;
$$ language plpgsql;

drop trigger if exists a_iu_Продукция_в_заказе on "Продукция_в_заказе";
create or replace trigger a_iu_Продукция_в_заказе
after insert or update on "Продукция_в_заказе"
for each row
execute function tf_a_iu_Продукция_в_заказе();

create or replace function tf_a_d_Продукция_в_заказе()
    returns trigger as $$
begin
    call p_подсчет_суммы_заказа(old.ид_заказа);
    return null;
end;
$$ language plpgsql;

create or replace trigger a_d_Продукция_в_заказе
after delete on "Продукция_в_заказе"
for each row
execute function tf_a_d_Продукция_в_заказе();

-- 7.1. Проверка удаления типов продукции

create or replace function tf_b_d_Тип_продукции()
returns trigger as $$
declare
    var_количество_продукции int;
begin
    var_количество_продукции = (select count(*)
                from "Готовая_продукция" inner join "Тип_продукции"
                on "Готовая_продукция".ид_типа = "Тип_продукции".ид
                where "Готовая_продукция".ид_типа = old.ид);

    if var_количество_продукции > 0 then
        raise exception 'Нельзя удалять типы продукции которые присутствуют на складах';
    end if;

    var_количество_продукции = (select count(*)
            from "Продукция_в_заказе" inner join "Тип_продукции"
            on "Продукция_в_заказе".ид_типа = "Тип_продукции".ид
            where "Продукция_в_заказе".ид_типа = old.ид);

    if var_количество_продукции > 0 then
        raise exception 'Нельзя удалять типы продукции которые были проданы';
    end if;

    return old;
end
$$ language plpgsql;


create or replace trigger b_d_Тип_продукции
before delete on "Тип_продукции"
for each row
execute function tf_b_d_Тип_продукции();

-- 7.2. Проверка удаления типов материалов

create or replace function tf_b_d_Тип_материала()
returns trigger as $$
declare
    var_количество_материалов int;
begin
    var_количество_материалов = (select count(*)
            from "Материалы" inner join "Тип_материала"
            on "Материалы".ид_типа = "Тип_материала".ид
            where "Материалы".ид_типа = old.ид);

    if var_количество_материалов > 0 then
        raise exception 'Нельзя удалять типы материлов которые присутствуют на складах';
    end if;

    return old;
end
$$ language plpgsql;


create or replace trigger b_d_Тип_материала
before delete on "Тип_материала"
for each row
execute function tf_b_d_Тип_материала();

-- 8.1. Проверка удаления складов сырья

create or replace function tf_b_d_Склад_сырья()
returns trigger as $$
declare
    var_материалы int;
begin
    var_материалы = (select count(*)
                  from "Материалы" inner join "Склад_сырья"
                  on "Материалы".ид_склада = "Склад_сырья".ид
                  where "Материалы".ид_склада = old.ид);

    if var_материалы > 0 then
        raise exception 'Нельзя удалять не пустые склады сырья (% типов)', var_материалы;
    end if;

    return old;
end
$$ language plpgsql;


create or replace trigger b_d_Склад_сырья
before delete on "Склад_сырья"
for each row
execute function tf_b_d_Склад_сырья();

-- 8.2. Проверка удаления складов готовой продукции

create or replace function tf_b_d_Склад_готовой_продукции()
returns trigger as $$
declare
    var_продукция int;
begin
    var_продукция = (select count(*)
                 from "Готовая_продукция" inner join "Склад_готовой_продукции"
                 on "Готовая_продукция".ид_склада = "Склад_готовой_продукции".ид
                 where "Готовая_продукция".ид_склада = old.ид);

    if var_продукция > 0 then
        raise exception 'Нельзя удалять не пустые склады готовой продукции (% типов)', var_продукция;
    end if;

    return old;
end
$$ language plpgsql;


create or replace trigger b_d_Склад_готовой_продукции
before delete on "Склад_готовой_продукции"
for each row
execute function tf_b_d_Склад_готовой_продукции();

-- 9. Проверка удаления цехов

create or replace function tf_b_d_Цех()
returns trigger as $$
declare
    var_сотрудники int;
begin
    var_сотрудники = (select count(*)
                 from "Сотрудник" inner join "Цех"
                 on "Сотрудник".ид_цеха = "Цех".ид
                 where "Сотрудник".ид_цеха = old.ид);

    if var_сотрудники > 0 then
        raise exception 'Нельзя удалять не пустые цехи (% сотрудников)', var_сотрудники;
    end if;

    return old;
end
$$ language plpgsql;


create or replace trigger b_d_Цех
before delete on "Цех"
for each row
execute function tf_b_d_Цех();

-- 10. Проверка удаления заводов

create or replace function tf_b_d_Завод()
returns trigger as $$
declare
    var_цехи int;
    var_склады_сырья int;
    var_склады_готовой_продукции int;
begin
    var_цехи = (select count(*)
                  from "Цех" inner join "Завод"
                  on "Цех".ид_завода = "Завод".ид
                  where "Цех".ид_завода = old.ид);

    var_склады_сырья = (select count(*)
            from "Склад_сырья" inner join "Завод"
            on "Склад_сырья".ид_завода = "Завод".ид
            where "Склад_сырья".ид_завода = old.ид);

    var_склады_готовой_продукции = (select count(*)
                    from "Склад_готовой_продукции" inner join "Завод"
                    on "Склад_готовой_продукции".ид_завода = "Завод".ид
                    where "Склад_готовой_продукции".ид_завода = old.ид);

    if var_цехи > 0 or var_склады_сырья > 0 or var_склады_готовой_продукции > 0 then
        raise exception 'Нельзя удалять заводы к которым приписаны цехи (%) и склады (сырья: % | готовой продукции: %)', var_цехи, var_склады_сырья, var_склады_готовой_продукции;
    end if;

    return old;
end
$$ language plpgsql;


create or replace trigger b_d_Завод
before delete on "Завод"
for each row
execute function tf_b_d_Завод();

-- 11. Проверка удаления службы поддержки

create or replace function tf_b_d_Служба_поддержки()
returns trigger as $$
declare
    var_сотрудники int;
begin
    var_сотрудники = (select count(*)
                  from "Консультант" inner join "Служба_поддержки"
                  on "Консультант".ид_службы_поддержки = "Служба_поддержки".ид
                  where "Консультант".ид_службы_поддержки = old.ид);

    if var_сотрудники > 0 then
        raise exception 'Нельзя удалять не пустые службы поддержки (% сотрудников)', var_сотрудники;
    end if;

    return old;
end
$$ language plpgsql;


create or replace trigger b_d_Служба_поддержки
before delete on "Служба_поддержки"
for each row
execute function tf_b_d_Служба_поддержки();
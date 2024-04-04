import itertools
import pandas as pd
import random
import datetime


password_alphabet = [chr(num) for num in range(ord("A"), ord("Z") + 1)]
password_alphabet.extend([chr(num) for num in range(ord("a"), ord("z") + 1)])
password_alphabet.extend([chr(num) for num in range(ord("0"), ord("9") + 1)])


def drop_substrings(text: str, substrings: list):
    new_text = text
    for sub in substrings:
        new_text = new_text.replace(sub, "")
    return new_text


class Generator:
    pwd_alphabet = password_alphabet

    @staticmethod
    def permutations_list(options: list):
        return [list(perm) for perm in itertools.permutations(options)]

    @staticmethod
    def random_symbols(left: int, right: int, length: int):
        return "".join(chr(random.randint(left, right)) for i in range(length))

    @staticmethod
    def gen_password(length: int):
        return "".join([Generator.pwd_alphabet[random.randint(0, len(Generator.pwd_alphabet) - 1)] for _ in range(length)])

    @staticmethod
    def parse_text_to_substrings(text: str):
        return [sub.strip() for sub in text.split(".")]

    @staticmethod
    def gen_date(start: datetime.date, step: datetime.timedelta, count: int):
        return [str(start + step * i) for i in range(count)]

    @staticmethod
    def gen_datetime(start: datetime.datetime, step: datetime.timedelta, count: int):
        return [str(start + step * i) for i in range(count)]


# drop_that = [",", "–", "-", "", "…", "\n", ":", ";", "\'", "\"", "?", "!", "(", ")"]
drop_that = []

with open("data/long_text.txt", encoding="utf-8") as long_text_file:
    data = drop_substrings(long_text_file.read(), drop_that)
    text_substrings = Generator.parse_text_to_substrings(data)
    long_text_file.close()

text_substrings_set = set(text_substrings)
print(len(text_substrings), len(text_substrings_set))

text_substrings_set_list = list(text_substrings_set)

# Заводы

factories = pd.read_csv("data/factories.csv", delimiter=";")
factories_names = factories["name"].to_list()
factories_count = len(factories_names)
factories_phone = factories["phone_number"].to_list()
factories_email = factories["email"].to_list()
factories_address = factories["address"].to_list()
factories_passwords = [Generator.gen_password(random.randint(5, 10)) for _ in range(factories_count)]

# Склады продукции

product_storages = pd.read_csv("data/product_storages.csv", delimiter=";")
p_storages_address = product_storages["address"]
product_storages_count = len(p_storages_address)
p_storages_factories_id = [random.randint(1, factories_count) for _ in range(product_storages_count)]

# Продукция

products = pd.read_csv("data/products.csv", delimiter=";")
products_names = products["name"].to_list()
products_count = len(products_names)
products_prices = [str(random.randint(10, 100) * random.randint(10, 100)) for _ in range(products_count)]

products_descriptions = []
for i in range(products_count):
    random.shuffle(text_substrings_set_list)
    products_descriptions.append(" ".join(text_substrings_set_list[:random.randint(4, 10)]))

# Готовая продукция

r_p_count = products_count * product_storages_count
r_p_storage_id = []
r_p_type_id = []
r_p_product_count = []

for i in range(product_storages_count):
    for j in range(products_count):
        r_p_storage_id.append(str(i + 1))
        r_p_type_id.append(str(j + 1))
        r_p_product_count.append(str(random.randint(10 ** 5, 10 ** 7)))


# Типы материалов

materials = pd.read_csv("data/materials.csv", delimiter=";")
materials_names = products["name"].to_list()
materials_count = len(materials_names)
materials_prices = [str(random.randint(10, 100) * random.randint(10, 100)) for _ in range(materials_count)]

materials_descriptions = []
for i in range(materials_count):
    random.shuffle(text_substrings_set_list)
    materials_descriptions.append(" ".join(text_substrings_set_list[:random.randint(4, 10)]))

# Склады материалов

material_storages = pd.read_csv("data/product_storages.csv", delimiter=";")
m_storages_address = material_storages["address"]
material_storages_count = len(m_storages_address)
m_storages_factories_id = [random.randint(1, factories_count) for _ in range(material_storages_count)]

# Материалы на складе

s_m_count = materials_count * material_storages_count
s_m_storage_id = []
s_m_type_id = []
s_m_material_count = []

for i in range(material_storages_count):
    for j in range(materials_count):
        s_m_storage_id.append(str(i + 1))
        s_m_type_id.append(str(j + 1))
        s_m_material_count.append(str(random.randint(10 ** 5, 10 ** 7)))


# Клиенты

client_names = pd.read_csv("data/organizations.csv", delimiter=";")["name"].to_list()
client_count = len(client_names)
registered_client_count = client_count // 2

client_emails = pd.read_csv("data/emails.csv", delimiter=";")["email"].to_list()
client_phones = ["+7" + Generator.random_symbols(ord("0"), ord("9"), 10) for _ in range(client_count)]
client_passwords = [Generator.gen_password(random.randint(5, 10)) for _ in range(client_count)]

# Сервисы

services = pd.read_csv("data/client_services.csv", delimiter=";")
services_names = services["name"].to_list()
services_count = len(services_names)
services_phone = services["phone_number"].to_list()
services_email = services["email"].to_list()
services_address = services["address"].to_list()

# Расписания

schedules = pd.read_csv("data/schedules.csv", delimiter=";")
schedules_hours = schedules["hours"].to_list()
schedules_count = len(schedules_hours)
schedules_description = schedules["description"].to_list()

# Консультанты

admins = pd.read_csv("data/admins.csv", delimiter=",")
admins_names = admins["name"].to_list()
admins_count = len(admins_names)
admins_phone = admins["phone_number"].to_list()
admins_email = admins["email"].to_list()
admins_schedule = [random.randint(1, schedules_count) for _ in range(admins_count)]
admins_service = [random.randint(1, services_count) for _ in range(admins_count)]
admins_passwords = [Generator.gen_password(random.randint(5, 10)) for _ in range(admins_count)]

# Заказы

order_status_options = ["ожидает оплаты", "выполняется", "выполнен", "отклонен"]
status_options_count = len(order_status_options)

orders_count = registered_client_count * 4
orders_client_id = [random.randint(1, registered_client_count) for _ in range(orders_count)]
orders_admin_id = [random.randint(1, admins_count) for _ in range(orders_count)]
orders_statuses = [order_status_options[random.randint(0, status_options_count - 1)] for _ in range(orders_count)]

orders_formed_at_datetime = [datetime.datetime(2023, 1, 1) + datetime.timedelta(days=random.random() * 30) for _ in range(orders_count)]
orders_formed_at = [str(v) for v in orders_formed_at_datetime]

orders_done_at_datetime = [v + datetime.timedelta(days=(random.random() + 0.5) * 20) for v in orders_formed_at_datetime]
orders_done_at = [str(v) for v in orders_done_at_datetime]

# Сообщения

sender = ["консультант", "клиент"]

max_messages_in_order_count = 4
total_messages_count = 0
messages_orders = []
messages_senders = []
messages_content = []
messages_datetime = []

for i in range(orders_count):
    current_messages_count = random.randint(max_messages_in_order_count // 2, max_messages_in_order_count)
    total_messages_count += current_messages_count
    messages_orders.extend([i + 1 for _ in range(current_messages_count)])
    messages_senders.extend([sender[random.randint(0, 1)] for _ in range(current_messages_count)])

    for j in range(current_messages_count):
        random.shuffle(text_substrings_set_list)
        messages_content.append(" ".join(text_substrings_set_list[:random.randint(1, 4)]))

    messages_datetime.extend([orders_formed_at_datetime[i] + (orders_done_at_datetime[i] - orders_formed_at_datetime[i]) * (i + random.random() / 2 + 0.25) / current_messages_count for j in range(current_messages_count)])

# Продукция в заказе

pio_status_options = ["ожидает производства", "ожидает сборки", "ожидает возвращения", "возвращена", "собрана под заказ"]

product_types = [i for i in range(1, products_count + 1)]

max_pio_types_count = 10
pio_total_count = 0
pio_order_id = []
pio_product_id = []
pio_status = []
pio_product_count = []

for i in range(orders_count):
    current_pio_types_count = random.randint(max_pio_types_count // 2, max_pio_types_count)
    pio_total_count += current_pio_types_count
    pio_order_id.extend([i + 1 for _ in range(current_pio_types_count)])

    random.shuffle(product_types)
    pio_product_id.extend(product_types[:current_pio_types_count])

    if orders_statuses[i] == "отклонен":
        pio_status.extend(["возвращена" for _ in range(current_pio_types_count)])
    elif orders_statuses[i] == "выполнен":
        pio_status.extend(["собрана под заказ" for _ in range(current_pio_types_count)])
    elif orders_statuses[i] == "выполняется":
        pio_status.extend(["ожидает сборки" for _ in range(current_pio_types_count)])
    else:
        pio_status.extend(["ожидает производства" for _ in range(current_pio_types_count)])

    pio_product_count.extend([random.randint(1, 10) for _ in range(current_pio_types_count)])


enum_data = []
lines = []


def data_sql():
    enum_data.append("drop type if exists Доступные_организации_enum;")
    enum_data.append("create type Доступные_организации_enum as enum (\n{}\n);".format(
        ",\n".join(["'{}'".format(v) for v in client_names])
    ))

    for i in range(registered_client_count):
        lines.append("select \"f_i_Клиент\"('{}', '{}', '{}', '{}');".format(
            client_phones[i],
            client_emails[i],
            client_passwords[i],
            client_names[i]
        ))

    for i in range(services_count):
        lines.append("select \"f_i_Служба_поддержки\"('{}', '{}', '{}', '{}');".format(
            services_names[i],
            services_phone[i],
            services_email[i],
            services_address[i]
        ))

    for i in range(schedules_count):
        lines.append("select \"f_i_Расписание_консультантов\"({}, '{}');".format(
            schedules_hours[i],
            schedules_description[i]
        ))

    for i in range(admins_count):
        lines.append("select \"f_i_Консультант\"({}, {}, '{}', '{}', '{}', '{}');".format(
            admins_service[i],
            admins_schedule[i],
            admins_names[i],
            admins_phone[i],
            admins_email[i],
            admins_passwords[i]
        ))

    for i in range(factories_count):
        lines.append("select \"f_i_Завод\"('{}', '{}', '{}', '{}', '{}');".format(
            factories_names[i],
            factories_phone[i],
            factories_email[i],
            factories_address[i],
            factories_passwords[i]
        ))

    for i in range(product_storages_count):
        lines.append("select \"f_i_Склад_готовой_продукции\"({}, '{}');".format(
            p_storages_factories_id[i],
            p_storages_address[i]
        ))

    for i in range(products_count):
        lines.append("select \"f_i_Тип_продукции\"({}, '{}', $${}$$);".format(
            products_prices[i],
            products_names[i],
            products_descriptions[i]
        ))

    for i in range(r_p_count):
        lines.append("call \"p_u_принести_продукцию_на_склад\"({}, {}, {});".format(
            r_p_storage_id[i],
            r_p_type_id[i],
            r_p_product_count[i]
        ))

    for i in range(material_storages_count):
        lines.append("select \"f_i_Склад_сырья\"({}, '{}');".format(
            m_storages_factories_id[i],
            m_storages_address[i]
        ))

    for i in range(products_count):
        lines.append("select \"f_i_Тип_материала\"({}, '{}', $${}$$);".format(
            materials_prices[i],
            materials_names[i],
            materials_descriptions[i]
        ))

    for i in range(s_m_count):
        lines.append("call \"p_u_принести_материалы_на_склад\"({}, {}, {});".format(
            s_m_storage_id[i],
            s_m_type_id[i],
            s_m_material_count[i]
        ))

    last_forming_order_client = 0
    for i in range(orders_count):
        lines.append("select \"f_ig_новый_заказ\"({}, '{}');".format(
            orders_client_id[i],
            orders_formed_at[i]
        ))

        for j in range(pio_total_count):
            if pio_order_id[j] == i + 1:
                lines.append("call \"p_i_добавить_продукцию_к_заказу\"({}, {});".format(
                    pio_order_id[j],
                    pio_product_id[j]
                ))

                lines.append("call \"p_u_изменить_количество_продукции\"({}, {}, {});".format(
                    pio_order_id[j],
                    pio_product_id[j],
                    pio_product_count[j]
                ))

                if pio_status[j] == "возвращена":
                    lines.append("call \"p_u_вернуть_продукцию_на_склады\"({}, {});".format(
                        pio_order_id[j],
                        pio_product_id[j]
                    ))
                elif pio_status[j] == "собрана под заказ":
                    lines.append("select \"f_u_собрать_продукцию_под_заказ\"({}, {});".format(
                        pio_order_id[j],
                        pio_product_id[j]
                    ))
                elif pio_status[j] == "ожидает сборки":
                    lines.append("call \"p_u_произвести_продукцию_под_заказ\"({}, {}, {});".format(
                        pio_order_id[j],
                        pio_product_id[j],
                        random.randint(1, product_storages_count)
                    ))

        # order_status_options = ["формируется", "ожидает оплаты", "выполняется", "выполнен", "отклонен"]

        if orders_statuses[i] == "ожидает оплаты":
            lines.append("call \"p_u_подтвердить_заказ\"({});".format(
                i + 1
            ))

        if orders_statuses[i] == "выполняется":
            lines.append("call \"p_u_подтвердить_заказ\"({});".format(
                i + 1
            ))

            lines.append("call \"p_u_оплата_заказа\"({});".format(
                i + 1
            ))

        if orders_statuses[i] == "выполнен":
            lines.append("call \"p_u_подтвердить_заказ\"({});".format(
                i + 1
            ))

            lines.append("call \"p_u_оплата_заказа\"({});".format(
                i + 1
            ))

            lines.append("call \"p_u_заказ_выполнен\"({}, '{}');".format(
                i + 1,
                orders_done_at[i]
            ))

        if orders_statuses[i] == "отклонен":
            lines.append("call \"p_u_подтвердить_заказ\"({});".format(
                i + 1
            ))

            lines.append("call \"p_u_заказ_отклонен\"({}, '{}');".format(
                i + 1,
                orders_done_at[i]
            ))

    for i in range(total_messages_count):
        if messages_senders[i] == "клиент":
            lines.append("call \"p_i_сообщение_клиента\"({}, $${}$$, '{}');".format(
                messages_orders[i],
                messages_content[i],
                messages_datetime[i]
            ))
        else:
            lines.append("call \"p_i_сообщение_консультанта\"({}, $${}$$, '{}');".format(
                messages_orders[i],
                messages_content[i],
                messages_datetime[i]
            ))


data_sql()

with open("output.txt", "w", encoding="utf-8") as out:
    out.write("\n".join(lines))
    out.flush()
    out.close()

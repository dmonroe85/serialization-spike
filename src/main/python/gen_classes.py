# noinspection PyInterpreter
import csv
import sys, os

# Constants
SCALA = 'scala'
JAVA = 'java'

UNPACKED = 'unpacked'
PACKED = 'packed'

# Assume these are parameters
language = JAVA
packing = UNPACKED
class_name = 'MyClass'

infile_name = os.path.join(os.pardir, 'resources', '{}.csv'.format(class_name))
outpath = os.path.join(os.pardir, language, package, class_name + '.' + language)


def cap_first(s):
    return s[0].upper() + s[1:]


def gen_spacing(offset):
    return ' ' * (offset)


def get_field_name(nm, n):
    return '{}{}'.format(nm, n)


def scala_field_up(nm, tp, ini):
    return '{}: {} = {}'.format(nm, tp, ini)

def scala_method_up(nm, tp, idx):
    return 'def get{}: {} = {}'.format(cap_first(nm), tp, nm)



methods = {
    SCALA + UNPACKED: [scala_field_up, scala_method_up],
    SCALA + PACKED: [],
    JAVA + UNPACKED: [],
    JAVA + PACKED: []
}

f_field, f_method = methods[language + packing]

class_field_list = []
class_method_list = []
with open(infile_name, 'r') as infile:
    reader = csv.reader(infile)
    field_index = 0
    for row in reader:
        param_name, param_type, init_val, n_copies = row

        for i in range(int(n_copies)):
            field_name = get_field_name(param_name, i)
            class_field_list.append(f_field(field_name, param_type, init_val))
            class_method_list.append(f_method(field_name, param_type, field_index))

            field_index += 1

class_field_str = (',\n' + gen_spacing(len(class_name) + 7)).join(class_field_list)
class_method_str = ('\n' + gen_spacing(4)).join(class_method_list)

result_scala = """package memtest.domain

import memtest.packing.PackingUtil;
import memtest.common.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

class %s(%s) {
    %s
}
""" % (class_name, class_field_str, class_method_str)

print(os.getcwd())

with open(outpath, 'w') as outfile:
    outfile.write(result_scala)

#!/usr/bin/env pyhton3
#
# Copyright 2017 Arjun Rao
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import random
import sys

def randomize_csv(infile, outfile):
    ''' randomize_csv randomizes rows in a csv file'''
    file_read = open(infile, "r")
    csv_lines = file_read.readlines()
    data = csv_lines[1:]
    labels = csv_lines[0]
    file_read.close()
    random.shuffle(data)
    file_read = open(outfile, "w")
    file_read.writelines(labels)
    file_read.writelines(data)
    file_read.close()

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: shufflecsv <source> <destination>")
    else:
        randomize_csv(sys.argv[1], sys.argv[2])

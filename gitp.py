import sys
import os

def main(commit):
    print("pushing commit: " + commit);
    os.system('git add .')
    os.system('git commit -m "' + commit + '"')
    os.system('git push -u origin master')

if __name__ == "__main__":
    main(sys.argv[1])

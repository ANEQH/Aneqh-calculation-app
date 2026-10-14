import re

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    lines = f.readlines()

for i, line in enumerate(lines):
    if "StatCard(label =" in line:
        pass
    if "113:" in line or "114:" in line or "115:" in line:
        print(f"Line {i+1}: {line.strip()}")

print("Line 112:", lines[111].strip())
print("Line 113:", lines[112].strip())
print("Line 114:", lines[113].strip())
print("Line 115:", lines[114].strip())


with open("app/src/main/java/com/example/ui/screens/SolversScreen.kt", "r") as f:
    lines = f.readlines()

out = []
for line in lines:
    out.append(line)
    if "SolverType.CROSS_MULTIPLY -> CrossMultiplySolverView()" in line:
        out.insert(len(out)-1, "                    SolverType.PYQ_SUGAR_PRICE -> PyqSugarPriceSolverView()\n")
        out.insert(len(out)-1, "                    SolverType.PYQ_ELECTION -> PyqElectionSolverView()\n")
        out.insert(len(out)-1, "                    SolverType.PYQ_PASS_FAIL -> PyqPassFailSolverView()\n")

with open("app/src/main/java/com/example/ui/screens/SolversScreen.kt", "w") as f:
    f.writelines(out)


package com.example.data.repository

data class MathConcept(
    val title: String,
    val formula: String,
    val trick: String,
    val pyqContext: String
)

object SscCglAdvancedMath {
    val concepts = listOf(
        MathConcept(
            "Algebra: Perfect Square Concept",
            "(a + b)² = a² + b² + 2ab\n(a - b)² = a² + b² - 2ab",
            "Trick: x + 1/x = k, then x² + 1/x² = k² - 2. x³ + 1/x³ = k³ - 3k.",
            "PYQ: Frequently asked in SSC CGL Tier 1 & Tier 2. Directly put k to find higher powers in 2 seconds."
        ),
        MathConcept(
            "Algebra: Power 5 and Power 6",
            "x^5 + 1/x^5 = (x² + 1/x²)(x³ + 1/x³) - (x + 1/x)",
            "Trick: For x^6 + 1/x^6, calculate cube first, then square it (k³-3k)² - 2.",
            "PYQ: Crucial for Tier 2 mains. Save 2 minutes using this shortcut."
        ),
        MathConcept(
            "Algebra: x + 1/x = √3",
            "x + 1/x = √3 => x^6 = -1",
            "Trick: If difference in powers is 6, the sum of those terms is 0 (e.g., x^12 + x^6 = 0).",
            "PYQ: Asked 15+ times in CGL history."
        ),
        MathConcept(
            "Algebra: a³ + b³ + c³ - 3abc",
            "Formula 1: (a+b+c)(a²+b²+c² - ab - bc - ca)\nFormula 2: 1/2(a+b+c)[(a-b)² + (b-c)² + (c-a)²]",
            "Trick: If a+b+c = 0, then a³+b³+c³ = 3abc. Use Formula 2 if values of a,b,c are consecutive.",
            "PYQ: Universal question in CGL Mains."
        ),
        MathConcept(
            "Trigonometry: Complementary Angles",
            "sin(A) = cos(B), tan(A) = cot(B), sec(A) = cosec(B) iff A + B = 90°",
            "Trick: tan(1°)tan(2°)...tan(89°) = 1 because tan(x)tan(90-x) = 1.",
            "PYQ: Extremely common in Tier 1."
        ),
        MathConcept(
            "Trigonometry: Max & Min Values",
            "a sinθ + b cosθ\nMax: +√(a² + b²)\nMin: -√(a² + b²)",
            "Trick: Always check if the equation matches this format before using calculus.",
            "PYQ: Basic standard result for SSC."
        ),
        MathConcept(
            "Trigonometry: Sec & Tan Relation",
            "sec²θ - tan²θ = 1 => (secθ - tanθ)(secθ + tanθ) = 1",
            "Trick: If secθ + tanθ = x, then secθ - tanθ = 1/x. Adding both gives 2secθ = x + 1/x.",
            "PYQ: CGL Tier 1 favorite."
        ),
        MathConcept(
            "Geometry: Incenter (अंतःकेंद्र)",
            "Angle at incenter = 90° + (A/2)",
            "Trick: Incenter is intersection of angle bisectors. Inradius(r) = Area/Semi-perimeter.",
            "PYQ: Direct formula based questions in geometry."
        ),
        MathConcept(
            "Geometry: Circumcenter (परिकेंद्र)",
            "Angle at circumcenter = 2A",
            "Trick: Intersection of perpendicular bisectors. Circumradius(R) = abc/4Area.",
            "PYQ: Used in equilateral and right angled triangles often."
        ),
        MathConcept(
            "Geometry: Orthocenter (लंबकेंद्र)",
            "Angle at orthocenter = 180° - A",
            "Trick: Intersection of altitudes.",
            "PYQ: Frequently confused with incenter. Remember 180-A."
        ),
        MathConcept(
            "Geometry: Centroid (केंद्रक)",
            "Divides median in 2:1 ratio.",
            "Trick: Apollonius Theorem: AB² + AC² = 2(AD² + BD²) where AD is median.",
            "PYQ: Heavily tested in triangles."
        ),
        MathConcept(
            "Geometry: Chord Intersections",
            "Internal: PA × PB = PC × PD\nExternal: PA × PB = PC × PD",
            "Trick: Tangent Secant Theorem: PT² = PA × PB",
            "PYQ: Circles most repeated concept."
        ),
        MathConcept(
            "Geometry: Common Tangents",
            "Direct: √(d² - (r1 - r2)²)\nTransverse: √(d² - (r1 + r2)²) where d is distance between centers.",
            "Trick: Direct is always longer than Transverse.",
            "PYQ: Standard CGL mains question."
        ),
        MathConcept(
            "Mensuration: Sphere",
            "Volume = 4/3 πr³\nSurface Area = 4πr²",
            "Trick: If a sphere is melted to form n smaller spheres, n = (R/r)³.",
            "PYQ: Melting and casting volume equivalency."
        ),
        MathConcept(
            "Mensuration: Cone and Cylinder",
            "Cone Vol = 1/3 πr²h. Cylinder Vol = πr²h.",
            "Trick: If height and radius are same, Cylinder Volume is exactly 3 times Cone Volume.",
            "PYQ: Ratio of volumes."
        ),
        MathConcept(
            "Mensuration: Frustum of Cone",
            "Vol = 1/3 πh(R² + r² + Rr)\nL.S.A = πl(R+r)",
            "Trick: Remember it as a modified cone formula.",
            "PYQ: Tier 2 specifically asks this."
        ),
        MathConcept(
            "Mensuration: Prism & Pyramid",
            "Prism Vol = Base Area × h\nPyramid Vol = 1/3 × Base Area × h",
            "Trick: Same relation as Cylinder and Cone.",
            "PYQ: Find base area first, then directly apply."
        ),
        MathConcept(
            "Number System: Divisibility Rules",
            "3 & 9: Sum of digits.\n4: Last 2 digits.\n8: Last 3 digits.\n11: Diff of sum of alternate digits is 0 or multiple of 11.",
            "Trick: For 7, 11, 13, difference of triplets from right to left must be divisible.",
            "PYQ: Questions like 'is 72x89y divisible by 72? Check 8 and 9.'"
        ),
        MathConcept(
            "Number System: Number of Factors",
            "If N = p^a × q^b × r^c, Factors = (a+1)(b+1)(c+1)",
            "Trick: Odd factors = take only odd prime powers. Even = Total - Odd.",
            "PYQ: Find total factors of 10800."
        ),
        MathConcept(
            "Number System: Remainder Theorem",
            "Fermat's: a^(p-1) / p leaves remainder 1 (if a,p coprime).\nWilson's: (p-1)! / p leaves remainder p-1.",
            "Trick: Use negative remainders to simplify calculations.",
            "PYQ: Highly advanced number system questions."
        ),
        MathConcept(
            "Arithmetic: Successive Discount",
            "D1 + D2 - (D1×D2)/100",
            "Trick: For 3 discounts, find equivalent of first two, then combine with third.",
            "PYQ: Profit & Loss staple."
        ),
        MathConcept(
            "Arithmetic: Dishonest Dealer",
            "Profit% = (Error / True Value) × 100",
            "Trick: Calculate everything on 1000g. Suppose he gives 800g for the price of 1000g, Profit = 200/800 = 25%.",
            "PYQ: Very common in SSC."
        ),
        MathConcept(
            "Arithmetic: Mixture & Alligation",
            "Ratio = (d - m) / (m - c)",
            "Trick: Draw the cross diagram. Top left: cheaper, Top right: dearer, Center: mixture.",
            "PYQ: Can be used in SI, Profit Loss, Speed Distance as well."
        ),
        MathConcept(
            "Arithmetic: Replacement of Liquid",
            "Final quantity of pure liquid = Initial × (1 - x/V)^n",
            "Trick: Memorize this directly. x is replaced amount, V is total volume, n is times process repeated.",
            "PYQ: Standard mixture question."
        ),
        MathConcept(
            "Arithmetic: Relative Speed",
            "Same direction = S1 - S2\nOpposite = S1 + S2",
            "Trick: For trains crossing each other, total distance is ALWAYS Sum of Lengths (L1+L2).",
            "PYQ: Train and platform questions."
        ),
        MathConcept(
            "Arithmetic: Average Speed",
            "2xy / (x+y) if distance is same.",
            "Trick: If distance is divided into 3 equal parts, Avg Speed = 3xyz / (xy+yz+zx).",
            "PYQ: Direct formula application."
        ),
        MathConcept(
            "Arithmetic: Boat and Stream",
            "Downstream (D) = U_boat + U_stream\nUpstream (U) = U_boat - U_stream",
            "Trick: U_boat = (D+U)/2. U_stream = (D-U)/2.",
            "PYQ: Usually solved mentally if you know the trick."
        ),
        MathConcept(
            "Arithmetic: Installments (CI)",
            "P = x/(1+R/100) + x/(1+R/100)² ...",
            "Trick: For 2 years, use ratio of amounts. If rate is 10% (1/10), ratio is 10:11. For 2 years: 100:121. Equate installments.",
            "PYQ: Tough questions made easy with ratio."
        )
    )
}

package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ScholarshipCalculatorTest {

    private final ScholarshipCalculator calc = new ScholarshipCalculator();

    // ---------- Part A: happy path ----------

    @Test
    void letterGrade_95_isA() {
        assertEquals("A", calc.letterGrade(95));
    }

    @Test
    void letterGrade_85_isB() {
        assertEquals("B", calc.letterGrade(85));
    }

    @Test
    void gpa_95and85_is3point5() {
        assertEquals(3.5, calc.gpa(new double[]{95, 85}), 0.0001);
    }

    @Test
    void scholarship_highGpaEnoughCredits_is100() {
        assertEquals(100, calc.scholarship(3.9, 16, false));
    }

    @Test
    void scholarship_goodGpaEnoughCredits_is70() {
        assertEquals(70, calc.scholarship(3.5, 13, false));
    }


    // ---------- Part B: reach 100% branch + line ----------

    // letterGrade: invalid input (both sides of the ||)
    @Test
    void letterGrade_negative_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1));
    }

    @Test
    void letterGrade_above100_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101));
    }

    // letterGrade: the remaining grades
    @Test
    void letterGrade_75_isC() {
        assertEquals("C", calc.letterGrade(75));
    }

    @Test
    void letterGrade_65_isD() {
        assertEquals("D", calc.letterGrade(65));
    }

    @Test
    void letterGrade_50_isF() {
        assertEquals("F", calc.letterGrade(50));
    }

    // gpa: invalid input (both sides of the ||)
    @Test
    void gpa_null_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
    }

    @Test
    void gpa_emptyArray_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[]{}));
    }

    // gpa: switch cases C, D and default (F)
    @Test
    void gpa_CDF_is1() {
        // C=2, D=1, F=0 -> 3/3 = 1.0
        assertEquals(1.0, calc.gpa(new double[]{75, 65, 50}), 0.0001);
    }

    // scholarship: each condition of the first || separately
    @Test
    void scholarship_negativeGpa_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(-0.1, 15, false));
    }

    @Test
    void scholarship_gpaAbove4_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(4.1, 15, false));
    }

    @Test
    void scholarship_negativeCredits_throws() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(3.5, -1, false));
    }

    // scholarship: remaining paths
    @Test
    void scholarship_withViolation_is0() {
        assertEquals(0, calc.scholarship(3.9, 16, true));
    }

    @Test
    void scholarship_highGpaLowCredits_is50() {
        assertEquals(50, calc.scholarship(3.9, 10, false));
    }

    @Test
    void scholarship_midGpa_is50() {
        assertEquals(50, calc.scholarship(3.2, 13, false));
    }

    @Test
    void scholarship_lowGpa_is0() {
        assertEquals(0, calc.scholarship(2.5, 10, false));
    }
    // ---------- Part C: loop boundaries in gpa() (0 / 1 / 2+) ----------
    // 0 iterations: gpa_null_throws and gpa_emptyArray_throws (Part B)

    @Test
    void gpa_loopOnce_singleScore_is4() {
        assertEquals(4.0, calc.gpa(new double[]{95}), 0.0001);
    }

    @Test
    void gpa_loopMany_fourScores_is2point5() {
        // A=4, B=3, C=2, D=1 -> 10/4 = 2.5
        assertEquals(2.5, calc.gpa(new double[]{95, 85, 75, 65}), 0.0001);
    }

    // ---------- Part D: boundary values for letterGrade() ----------

    @Test
    void letterGrade_boundary_0_isF() {
        assertEquals("F", calc.letterGrade(0));
    }

    @Test
    void letterGrade_boundary_59_99_isF() {
        assertEquals("F", calc.letterGrade(59.99));
    }

    @Test
    void letterGrade_boundary_60_isD() {
        assertEquals("D", calc.letterGrade(60));
    }

    @Test
    void letterGrade_boundary_69_99_isD() {
        assertEquals("D", calc.letterGrade(69.99));
    }

    @Test
    void letterGrade_boundary_89_99_isB() {
        assertEquals("B", calc.letterGrade(89.99));
    }

    @Test
    void letterGrade_boundary_90_isA() {
        assertEquals("A", calc.letterGrade(90));
    }

    @Test
    void letterGrade_boundary_100_isA() {
        assertEquals("A", calc.letterGrade(100));
    }
}
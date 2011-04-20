/*
 Copyright (C) 2008 Richard Gomes

 This source code is release under the BSD License.

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the JQuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.

 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */

package cz.paulrz.montecarlo;

/**
 * PMT : Primitive Polynomials Modulo Two
 * <p>
 * The encoding is as follows: Coefficients of each primitive polynomial are
 * the bits of the given integer. The leading and trailing coefficients, which
 * are 1 for all of the polynomials, have been omitted.
 * <pre>
 * Example: The polynomial
 *
 *      4    2
 *     x  + x  + 1
 *
 * is encoded as  2  in the array of polynomials of degree 4 because the
 * binary sequence of coefficients
 *
 *   10101
 *
 * becomes
 *
 *    0101
 *
 * after stripping off the top bit, and this is converted to
 *
 *     010
 *
 * by right-shifting and losing the rightmost bit. Similarly, we have
 *
 *   5    4    2
 *  x  + x  + x  + x + 1
 *
 * encoded as  13  [ (1)1101(1) ]  in the array for degree 5.
 * </pre>
 * 
 * @note Replace this class by the 8129334 polinomials version
 * if you want absolutely all of the provided primitive polynomials modulo two.
 * 
 * @author Dominik Holenstein
 * @author Q. Boiler
 */
// TODO: code review :: please verify against QL/C++ code
// This class requires additional analysis
public class PrimitivePolynomials {

    //
    // constants
    //

    private static final long N_PRIMITIVES_UP_TO_DEGREE_01 = 1;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_02 = 2;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_03 = 4;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_04 = 6;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_05 = 12;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_06 = 18;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_07 = 36;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_08 = 52;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_09 = 100;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_10 = 160;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_11 = 336;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_12 = 480;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_13 = 1110;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_14 = 1866;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_15 = 3666;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_16 = 5714;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_17 = 13424;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_18 = 21200;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_19 = 48794;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_20 = 72794;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_21 = 157466;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_22 = 277498;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_23 = 634458;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_24 = 910938;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_25 = 2206938;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_26 = 3926838;
    private static final long N_PRIMITIVES_UP_TO_DEGREE_27 = 8129334;
    private static final long N_PRIMITIVES = N_PRIMITIVES_UP_TO_DEGREE_27;

    private static final long defaultPpmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_18;



    //
    // private fields
    //

    private final long ppmtMaxDim;
    private final int  nMaxDegree;
    private final long primitivePolynomials[][];


    //
    // public constructors
    //

    public PrimitivePolynomials() {
        this(defaultPpmtMaxDim);
    }

    public PrimitivePolynomials(final long ppmtMaxDim) {

        if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_01) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_01;
            this.nMaxDegree = 1;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_02) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_02;
            this.nMaxDegree = 2;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_03) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_03;
            this.nMaxDegree = 3;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_04) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_04;
            this.nMaxDegree = 4;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_05) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_05;
            this.nMaxDegree = 5;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_06) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_06;
            this.nMaxDegree = 6;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_07) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_07;
            this.nMaxDegree = 7;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_08) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_08;
            this.nMaxDegree = 8;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_09) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_09;
            this.nMaxDegree = 9;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_10) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_10;
            this.nMaxDegree = 10;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_11) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_11;
            this.nMaxDegree = 11;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_11) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_11;
            this.nMaxDegree = 11;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_12) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_12;
            this.nMaxDegree = 12;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_13) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_13;
            this.nMaxDegree = 13;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_14) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_14;
            this.nMaxDegree = 14;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_15) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_15;
            this.nMaxDegree = 15;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_16) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_16;
            this.nMaxDegree = 16;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_17) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_17;
            this.nMaxDegree = 17;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_18) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_18;
            this.nMaxDegree = 18;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_19) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_19;
            this.nMaxDegree = 19;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_20) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_20;
            this.nMaxDegree = 20;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_21) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_21;
            this.nMaxDegree = 21;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_22) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_22;
            this.nMaxDegree = 22;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_23) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_23;
            this.nMaxDegree = 23;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_24) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_24;
            this.nMaxDegree = 24;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_25) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_25;
            this.nMaxDegree = 25;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_26) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_26;
            this.nMaxDegree = 26;
        } else if (ppmtMaxDim <= N_PRIMITIVES_UP_TO_DEGREE_27) {
            this.ppmtMaxDim = N_PRIMITIVES_UP_TO_DEGREE_27;
            this.nMaxDegree = 27;
        } else
            throw new ArithmeticException("ppmtMaxDim cannot be greater than N_PRIMITIVES");

        this.primitivePolynomials = new long[this.nMaxDegree][];
        applyPrimitivePolynomialDegree00();
        applyPrimitivePolynomialDegree01();
        applyPrimitivePolynomialDegree02();
        applyPrimitivePolynomialDegree03();
        applyPrimitivePolynomialDegree04();
        applyPrimitivePolynomialDegree05();
        applyPrimitivePolynomialDegree06();
        applyPrimitivePolynomialDegree07();
        applyPrimitivePolynomialDegree08();
        applyPrimitivePolynomialDegree09();
        applyPrimitivePolynomialDegree10();
        applyPrimitivePolynomialDegree11();
        applyPrimitivePolynomialDegree12();
        applyPrimitivePolynomialDegree13();
        applyPrimitivePolynomialDegree14();
        applyPrimitivePolynomialDegree15();
        applyPrimitivePolynomialDegree16();
        applyPrimitivePolynomialDegree17();
        applyPrimitivePolynomialDegree18();
    }


    //
    // public methods
    //

    public final long get(final int i, final int j) /*@ReadOnly*/ {
        return primitivePolynomials[i][j];
    }

    public final long getPpmtMaxDim() /*@ReadOnly*/ {
        return ppmtMaxDim;
    }


    //
    // private methods
    //

    private void applyPrimitivePolynomialDegree00() {
        /**
         * <pre>
         * x+1 (1)(1)
         * </pre>
         */
        final long PrimitivePolynomialDegree01[] = { 0, -1 };
        primitivePolynomials[0] = PrimitivePolynomialDegree01;
    }

    private void applyPrimitivePolynomialDegree01() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_01) {
            /**
             * <pre>
             * x^2+x+1 (1)1(1)
             * </pre>
             */
            final long PrimitivePolynomialDegree02[] = { 1, -1 };
            primitivePolynomials[1] = PrimitivePolynomialDegree02;
        }
    }

    private void applyPrimitivePolynomialDegree02() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_02) {
            /**
             * <pre>
             * x^3    +x+1 (1)01(1)
             * x^3+x^2  +1 (1)10(1)
             * </pre>
             */
            final long PrimitivePolynomialDegree03[] = { 1, 2, -1 };
            primitivePolynomials[2] = PrimitivePolynomialDegree03;
        }
    }

    private void applyPrimitivePolynomialDegree03() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_03) {
            /**
             * <pre>
             * x^4+       +x+1 (1)001(1)
             * x^4+x^3+     +1 (1)100(1)
             * </pre>
             */
            final long PrimitivePolynomialDegree04[] = { 1, 4, -1 };
            primitivePolynomials[3] = PrimitivePolynomialDegree04;
        }
    }

    private void applyPrimitivePolynomialDegree04() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_04) {
            /**
             * <pre>
             * x^5        +x^2  +1 (1)0010(1)
             * x^5    +x^3      +1 (1)0100(1)
             * x^5    +x^3+x^2+x+1 (1)0111(1)
             * x^5+x^4    +x^2+x+1 (1)1011(1)
             * x^5+x^4+x^3    +x+1 (1)1101(1)
             * x^5+x^4+x^3+x^2  +1 (1)1110(1)
             * </pre>
             */
            final long PrimitivePolynomialDegree05[] = { 2, 4, 7, 11, 13, 14, -1 };
            primitivePolynomials[4] = PrimitivePolynomialDegree05;
        }
    }

    private void applyPrimitivePolynomialDegree05() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_05) {
            /**
             * <pre>
             * x^6                +x+1 (1)00001(1)
             * x^6    +x^4+x^3    +x+1 (1)01101(1)
             * x^6+x^5              +1 (1)10000(1)
             * x^6            +x^2+x+1 (1)10011(1)
             * x^6+x^5    +x^3+x^2  +1 (1)10110(1)
             * x^6+x^5+x^4        +x+1 (1)11001(1)
             * </pre>
             */
            final long PrimitivePolynomialDegree06[] = { 1, 13, 16, 19, 22, 25, -1 };
            primitivePolynomials[5] = PrimitivePolynomialDegree06;
        }
    }

    private void applyPrimitivePolynomialDegree06() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_06) {
            /**
             * <pre>
             * x^7                    +x+1 (1)000001(1)
             * x^7            +x^3      +1 (1)000100(1)
             * x^7            +x^3+x^2+x+1 (1)000111(1)
             * x^7        +x^4          +1 (1)001000(1)
             * x^7        +x^4+x^3+x^2  +1 (1)001110(1)
             * x^7    +x^5        +x^2+x+1 (1)010011(1)
             * x^7    +x^5    +x^3    +x+1 (1)010101(1)
             * x^7    +x^5+x^4+x^3      +1 (1)011100(1)
             * x^7    +x^5+x^4+x^3+x^2+x+1 (1)011111(1)
             * x^7+x^6                  +1 (1)100000(1)
             * x^7+x^6        +x^3    +x+1 (1)100101(1)
             * x^7+x^6    +x^4        +x+1 (1)101001(1)
             * x^7+x^6    +x^4    +x^2  +1 (1)101010(1)
             * 32 polynomials so far ... let's go ahead
             * x^7+x^6+x^5        +x^2  +1 (1)110010(1)
             * x^7+x^6+x^5    +x^3+x^2+x+1 (1)110111(1)
             * x^7+x^6+x^5+x^4          +1 (1)111000(1)
             * x^7+x^6+x^5+x^4    +x^2+x+1 (1)111011(1)
             * x^7+x^6+x^5+x^4+x^3+x^2  +1 (1)111110(1)
             * </pre>
             */
            final long PrimitivePolynomialDegree07[] = { 1, 4, 7, 8, 14, 19, 21, 28, 31, 32, 37, 41, 42, 50, 55, 56, 59, 62, -1 };
            primitivePolynomials[6] = PrimitivePolynomialDegree07;
        }
    }

    private void applyPrimitivePolynomialDegree07() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_07) {
            final long PrimitivePolynomialDegree08[] = { 14, 21, 22, 38, 47, 49, 50, 52, 56, 67, 70, 84, 97, 103, 115, 122, -1 };
            primitivePolynomials[7] = PrimitivePolynomialDegree08;
        }
    }

    private void applyPrimitivePolynomialDegree08() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_08) {
            final long PrimitivePolynomialDegree09[] = { 8, 13, 16, 22, 25, 44, 47, 52, 55, 59, 62, 67, 74, 81, 82, 87, 91,
                    94, 103, 104, 109, 122, 124, 137, 138, 143, 145, 152, 157, 167, 173, 176, 181, 182, 185, 191, 194, 199, 218, 220, 227,
                    229, 230, 234, 236, 241, 244, 253, -1 };
            primitivePolynomials[8] = PrimitivePolynomialDegree09;
        }
    }

    private void applyPrimitivePolynomialDegree09() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_09) {
            final long PrimitivePolynomialDegree10[] = { 4, 13, 19, 22, 50, 55, 64, 69, 98, 107, 115, 121, 127, 134, 140,
                    145, 152, 158, 161, 171, 181, 194, 199, 203, 208, 227, 242, 251, 253, 265, 266, 274, 283, 289, 295, 301, 316, 319, 324,
                    346, 352, 361, 367, 382, 395, 398, 400, 412, 419, 422, 426, 428, 433, 446, 454, 457, 472, 493, 505, 508, -1 };
            primitivePolynomials[9] = PrimitivePolynomialDegree10;
        }
    }

    private void applyPrimitivePolynomialDegree10() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_10) {
            final long PrimitivePolynomialDegree11[] = { 2, 11, 21, 22, 35, 49, 50, 56, 61, 70, 74, 79, 84, 88, 103, 104,
                    112, 115, 117, 122, 134, 137, 146, 148, 157, 158, 162, 164, 168, 173, 185, 186, 191, 193, 199, 213, 214, 220, 227, 236,
                    242, 251, 256, 259, 265, 266, 276, 292, 304, 310, 316, 319, 322, 328, 334, 339, 341, 345, 346, 362, 367, 372, 375, 376,
                    381, 385, 388, 392, 409, 415, 416, 421, 428, 431, 434, 439, 446, 451, 453, 457, 458, 471, 475, 478, 484, 493, 494, 499,
                    502, 517, 518, 524, 527, 555, 560, 565, 569, 578, 580, 587, 589, 590, 601, 607, 611, 614, 617, 618, 625, 628, 635, 641,
                    647, 654, 659, 662, 672, 675, 682, 684, 689, 695, 696, 713, 719, 724, 733, 734, 740, 747, 749, 752, 755, 762, 770, 782,
                    784, 787, 789, 793, 796, 803, 805, 810, 815, 824, 829, 830, 832, 841, 847, 849, 861, 871, 878, 889, 892, 901, 908, 920,
                    923, 942, 949, 950, 954, 961, 968, 971, 973, 979, 982, 986, 998, 1001, 1010, 1012, -1 };
            primitivePolynomials[10] = PrimitivePolynomialDegree11;
        }
    }

    private void applyPrimitivePolynomialDegree11() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_11) {
            final long PrimitivePolynomialDegree12[] = { 41, 52, 61, 62, 76, 104, 117, 131, 143, 145, 157, 167, 171, 176,
                    181, 194, 217, 236, 239, 262, 283, 286, 307, 313, 319, 348, 352, 357, 391, 398, 400, 412, 415, 422, 440, 460, 465, 468,
                    515, 536, 539, 551, 558, 563, 570, 595, 598, 617, 647, 654, 678, 713, 738, 747, 750, 757, 772, 803, 810, 812, 850, 862,
                    906, 908, 929, 930, 954, 964, 982, 985, 991, 992, 1067, 1070, 1096, 1099, 1116, 1143, 1165, 1178, 1184, 1202, 1213,
                    1221, 1240, 1246, 1252, 1255, 1267, 1293, 1301, 1305, 1332, 1349, 1384, 1392, 1402, 1413, 1417, 1423, 1451, 1480, 1491,
                    1503, 1504, 1513, 1538, 1544, 1547, 1555, 1574, 1603, 1615, 1618, 1629, 1634, 1636, 1639, 1657, 1667, 1681, 1697, 1704,
                    1709, 1722, 1730, 1732, 1802, 1804, 1815, 1826, 1832, 1843, 1849, 1863, 1905, 1928, 1933, 1939, 1976, 1996, 2013, 2014,
                    2020, -1 };
            primitivePolynomials[11] = PrimitivePolynomialDegree12;
        }
    }

    private void applyPrimitivePolynomialDegree12() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_12) {
            final long[] PrimitivePolynomialDegree13 = { 13, 19, 26, 41, 50, 55, 69, 70, 79, 82, 87, 93, 94, 97, 100, 112, 121,
                    134, 138, 148, 151, 157, 161, 179, 181, 188, 196, 203, 206, 223, 224, 227, 230, 239, 241, 248, 253, 268, 274,
                    283, 286, 289, 301, 302, 316, 319, 324, 331, 333, 345, 351, 358, 375, 379, 381, 386, 403, 405, 419, 426, 428,
                    439, 440, 446, 451, 454, 458, 465, 468, 472, 475, 477, 496, 502, 508, 517, 521, 527, 530, 532, 542, 552, 555,
                    560, 566, 575, 577, 589, 590, 602, 607, 608, 611, 613, 625, 644, 651, 654, 656, 662, 668, 681, 682, 689, 696,
                    699, 707, 709, 714, 716, 719, 727, 734, 738, 743, 747, 757, 769, 770, 776, 790, 799, 805, 809, 812, 820, 827,
                    829, 835, 841, 844, 856, 859, 862, 865, 885, 890, 905, 916, 925, 935, 939, 942, 949, 953, 956, 961, 968, 976,
                    988, 995, 997, 1007, 1015, 1016, 1027, 1036, 1039, 1041, 1048, 1053, 1054, 1058, 1075, 1082, 1090, 1109, 1110,
                    1119, 1126, 1130, 1135, 1137, 1140, 1149, 1156, 1159, 1160, 1165, 1173, 1178, 1183, 1184, 1189, 1194, 1211,
                    1214, 1216, 1225, 1231, 1239, 1243, 1246, 1249, 1259, 1273, 1274, 1281, 1287, 1294, 1296, 1305, 1306, 1318,
                    1332, 1335, 1336, 1341, 1342, 1362, 1364, 1368, 1378, 1387, 1389, 1397, 1401, 1408, 1418, 1425, 1426, 1431,
                    1435, 1441, 1444, 1462, 1471, 1474, 1483, 1485, 1494, 1497, 1516, 1522, 1534, 1543, 1552, 1557, 1558, 1567,
                    1568, 1574, 1592, 1605, 1606, 1610, 1617, 1623, 1630, 1634, 1640, 1643, 1648, 1651, 1653, 1670, 1676, 1684,
                    1687, 1691, 1693, 1698, 1709, 1715, 1722, 1732, 1735, 1747, 1749, 1754, 1777, 1784, 1790, 1795, 1801, 1802,
                    1812, 1828, 1831, 1837, 1838, 1840, 1845, 1863, 1864, 1867, 1870, 1877, 1881, 1884, 1903, 1917, 1918, 1922,
                    1924, 1928, 1931, 1951, 1952, 1957, 1958, 1964, 1967, 1970, 1972, 1994, 2002, 2007, 2008, 2023, 2030, 2035,
                    2038, 2042, 2047, 2051, 2058, 2060, 2071, 2084, 2087, 2099, 2108, 2111, 2120, 2128, 2138, 2143, 2144, 2153,
                    2156, 2162, 2167, 2178, 2183, 2202, 2211, 2214, 2223, 2225, 2232, 2237, 2257, 2260, 2267, 2274, 2276, 2285,
                    2288, 2293, 2294, 2297, 2303, 2308, 2311, 2318, 2323, 2332, 2341, 2345, 2348, 2354, 2368, 2377, 2380, 2383,
                    2388, 2395, 2397, 2401, 2411, 2413, 2419, 2435, 2442, 2455, 2472, 2478, 2490, 2507, 2509, 2517, 2524, 2528,
                    2531, 2538, 2545, 2546, 2555, 2557, 2564, 2573, 2579, 2592, 2598, 2607, 2612, 2619, 2621, 2627, 2633, 2636,
                    2642, 2654, 2660, 2669, 2675, 2684, 2694, 2703, 2706, 2712, 2715, 2722, 2727, 2734, 2742, 2745, 2751, 2766,
                    2768, 2780, 2790, 2794, 2796, 2801, 2804, 2807, 2816, 2821, 2831, 2834, 2839, 2845, 2852, 2856, 2861, 2873,
                    2874, 2888, 2893, 2894, 2902, 2917, 2921, 2922, 2929, 2935, 2946, 2951, 2957, 2960, 2966, 2972, 2976, 2979,
                    2985, 3000, 3003, 3013, 3018, 3020, 3025, 3042, 3047, 3048, 3051, 3054, 3056, 3065, 3073, 3074, 3083, 3086,
                    3091, 3097, 3109, 3116, 3124, 3128, 3153, 3160, 3165, 3172, 3175, 3184, 3193, 3196, 3200, 3203, 3205, 3209,
                    3224, 3239, 3251, 3254, 3265, 3266, 3275, 3280, 3283, 3286, 3301, 3302, 3305, 3319, 3323, 3326, 3331, 3348,
                    3351, 3358, 3368, 3374, 3376, 3379, 3385, 3386, 3396, 3420, 3423, 3430, 3433, 3434, 3439, 3442, 3444, 3453,
                    3464, 3477, 3478, 3482, 3487, 3497, 3500, 3505, 3506, 3511, 3512, 3515, 3525, 3532, 3538, 3540, 3547, 3549,
                    3560, 3571, 3577, 3583, 3590, 3593, 3594, 3599, 3601, 3602, 3613, 3623, 3630, 3638, 3649, 3655, 3662, 3667,
                    3669, 3676, 3683, 3700, 3709, 3710, 3713, 3723, 3725, 3728, 3734, 3737, 3738, 3744, 3750, 3762, 3764, 3774,
                    3776, 3786, 3800, 3803, 3809, 3816, 3821, 3827, 3829, 3836, 3842, 3844, 3847, 3853, 3861, 3871, 3872, 3881,
                    3890, 3892, 3909, 3921, 3934, 3938, 3947, 3950, 3952, 3964, 3974, 3980, 3983, 3986, 3995, 3998, 4001, 4002,
                    4004, 4008, 4011, 4016, 4033, 4036, 4040, 4053, 4058, 4081, 4091, 4094, -1 };
            primitivePolynomials[12] = PrimitivePolynomialDegree13;
        }
    }

    private void applyPrimitivePolynomialDegree13() {

        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_13) {
            final long[] PrimitivePolynomialDegree14 = { 21, 28, 41, 47, 61, 84, 87, 93, 94, 103, 117, 121, 134, 137, 157, 161,
                    205, 206, 211, 214, 218, 234, 236, 248, 262, 299, 304, 319, 322, 334, 355, 357, 358, 369, 372, 375, 388, 400,
                    415, 446, 451, 458, 471, 484, 501, 502, 517, 545, 569, 617, 618, 623, 625, 637, 661, 668, 684, 695, 716, 719,
                    722, 731, 738, 747, 755, 761, 767, 775, 782, 787, 794, 803, 812, 817, 824, 829, 850, 866, 871, 877, 920, 935,
                    959, 979, 992, 1010, 1012, 1015, 1033, 1036, 1053, 1057, 1069, 1072, 1075, 1087, 1089, 1137, 1166, 1174, 1180,
                    1204, 1211, 1219, 1236, 1255, 1264, 1306, 1330, 1341, 1344, 1347, 1349, 1361, 1380, 1390, 1404, 1435, 1444,
                    1453, 1461, 1462, 1465, 1468, 1474, 1483, 1488, 1493, 1500, 1509, 1510, 1514, 1519, 1528, 1533, 1540, 1550,
                    1567, 1571, 1573, 1578, 1598, 1606, 1618, 1630, 1634, 1640, 1643, 1654, 1679, 1688, 1698, 1703, 1704, 1722,
                    1735, 1750, 1753, 1760, 1789, 1792, 1802, 1819, 1825, 1828, 1832, 1845, 1846, 1857, 1869, 1897, 1906, 1908,
                    1911, 1934, 1962, 1967, 1972, 1975, 1999, 2004, 2011, 2013, 2037, 2051, 2063, 2066, 2094, 2128, 2154, 2164,
                    2198, 2217, 2220, 2223, 2238, 2245, 2252, 2258, 2264, 2280, 2285, 2293, 2294, 2298, 2303, 2306, 2311, 2326,
                    2354, 2373, 2380, 2385, 2392, 2401, 2402, 2408, 2419, 2450, 2452, 2477, 2509, 2510, 2515, 2524, 2527, 2531,
                    2552, 2561, 2567, 2571, 2586, 2588, 2595, 2607, 2621, 2630, 2639, 2653, 2670, 2672, 2700, 2711, 2715, 2748,
                    2751, 2753, 2754, 2760, 2774, 2784, 2793, 2804, 2811, 2822, 2826, 2836, 2839, 2840, 2893, 2901, 2902, 2905,
                    2912, 2915, 2918, 2939, 2965, 2966, 2975, 2988, 2993, 3006, 3017, 3031, 3038, 3041, 3042, 3051, 3073, 3088,
                    3097, 3098, 3103, 3104, 3146, 3148, 3153, 3159, 3182, 3187, 3189, 3199, 3239, 3263, 3271, 3275, 3278, 3280,
                    3283, 3290, 3311, 3316, 3343, 3346, 3357, 3358, 3361, 3362, 3364, 3386, 3418, 3424, 3433, 3434, 3436, 3463,
                    3467, 3477, 3484, 3505, 3508, 3515, 3532, 3553, 3554, 3568, 3573, 3587, 3589, 3596, 3608, 3620, 3630, 3644,
                    3649, 3664, 3679, 3680, 3685, 3686, 3698, 3714, 3726, 3737, 3767, 3782, 3786, 3793, 3796, 3805, 3815, 3841,
                    3847, 3853, 3862, 3875, 3902, 3904, 3916, 3947, 3949, 3955, 3962, 3971, 3980, 3985, 3998, 4001, 4002, 4016,
                    4021, 4026, 4043, 4079, 4102, 4106, 4119, 4126, 4147, 4149, 4164, 4174, 4181, 4185, 4188, 4202, 4228, 4232,
                    4246, 4252, 4256, 4286, 4303, 4306, 4311, 4317, 4342, 4346, 4377, 4401, 4407, 4414, 4422, 4431, 4434, 4436,
                    4443, 4459, 4461, 4462, 4473, 4497, 4504, 4507, 4525, 4534, 4538, 4548, 4552, 4560, 4575, 4599, 4612, 4619,
                    4640, 4643, 4677, 4687, 4723, 4730, 4736, 4741, 4763, 4765, 4770, 4781, 4808, 4822, 4828, 4831, 4842, 4855,
                    4859, 4867, 4870, 4881, 4893, 4910, 4917, 4929, 4939, 4947, 4949, 4954, 4972, 4975, 5000, 5005, 5029, 5039,
                    5044, 5051, 5056, 5073, 5096, 5128, 5134, 5161, 5179, 5193, 5199, 5202, 5204, 5218, 5247, 5260, 5271, 5301,
                    5305, 5319, 5326, 5328, 5333, 5364, 5376, 5399, 5416, 5421, 5427, 5429, 5430, 5434, 5441, 5451, 5465, 5466,
                    5471, 5477, 5492, 5495, 5505, 5515, 5525, 5529, 5532, 5539, 5541, 5556, 5566, 5568, 5574, 5595, 5602, 5611,
                    5616, 5622, 5628, 5655, 5662, 5675, 5689, 5722, 5724, 5728, 5731, 5746, 5764, 5771, 5781, 5801, 5809, 5810,
                    5812, 5841, 5848, 5853, 5854, 5858, 5892, 5907, 5910, 5913, 5920, 5929, 5949, 5952, 5955, 5962, 5975, 5985,
                    5997, 5998, 6012, 6016, 6026, 6036, 6040, 6045, 6055, 6059, 6088, 6115, 6124, 6127, 6132, 6146, 6158, 6169,
                    6206, 6228, 6237, 6244, 6247, 6256, 6281, 6282, 6284, 6296, 6299, 6302, 6325, 6349, 6352, 6362, 6383, 6386,
                    6395, 6397, 6415, 6424, 6429, 6439, 6451, 6489, 6496, 6502, 6511, 6514, 6520, 6523, 6529, 6532, 6547, 6549,
                    6598, 6601, 6610, 6622, 6632, 6655, 6665, 6666, 6695, 6701, 6709, 6710, 6741, 6745, 6758, 6767, 6772, 6782,
                    6797, 6800, 6843, 6845, 6860, 6865, 6878, 6887, 6888, 6901, 6906, 6940, 6947, 6953, 6954, 6959, 6961, 6964,
                    6991, 6993, 6999, 7016, 7029, 7036, 7045, 7076, 7083, 7094, 7097, 7123, 7130, 7139, 7145, 7146, 7178, 7186,
                    7192, 7198, 7222, 7269, 7270, 7276, 7287, 7307, 7315, 7334, 7340, 7351, 7352, 7357, 7360, 7363, 7384, 7396,
                    7403, 7406, 7425, 7437, 7445, 7450, 7455, 7461, 7466, 7488, 7494, 7515, 7517, 7521, 7536, 7546, 7569, 7591,
                    7592, 7598, 7612, 7623, 7632, 7637, 7644, 7657, 7665, 7672, 7682, 7699, 7702, 7724, 7749, 7753, 7754, 7761,
                    7771, 7777, 7784, 7804, 7807, 7808, 7818, 7835, 7842, 7865, 7868, 7880, 7883, 7891, 7897, 7907, 7910, 7924,
                    7933, 7934, 7942, 7948, 7959, 7984, 7994, 7999, 8014, 8021, 8041, 8049, 8050, 8068, 8080, 8095, 8102, 8106,
                    8120, 8133, 8134, 8143, 8162, 8168, 8179, -1 };
            primitivePolynomials[13] = PrimitivePolynomialDegree14;
        }
    }

    private void applyPrimitivePolynomialDegree14() {

        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_14) {
            final long[] PrimitivePolynomialDegree15 = { 1, 8, 11, 22, 26, 47, 59, 64, 67, 73, 82, 97, 103, 110, 115, 122, 128,
                    138, 146, 171, 174, 176, 182, 194, 208, 211, 220, 229, 230, 239, 254, 265, 285, 290, 319, 324, 327, 333, 357,
                    364, 395, 397, 405, 409, 419, 422, 431, 433, 436, 440, 453, 460, 471, 478, 482, 488, 524, 529, 535, 536, 539,
                    563, 566, 572, 577, 587, 592, 602, 623, 635, 638, 654, 656, 659, 665, 675, 677, 687, 696, 701, 704, 710, 721,
                    728, 738, 740, 749, 758, 761, 772, 776, 782, 789, 810, 812, 818, 830, 832, 852, 855, 859, 865, 877, 895, 901,
                    902, 906, 916, 920, 949, 962, 964, 967, 981, 982, 985, 991, 1007, 1016, 1024, 1051, 1060, 1070, 1072, 1084,
                    1089, 1095, 1114, 1123, 1132, 1154, 1156, 1163, 1171, 1202, 1228, 1239, 1255, 1256, 1262, 1270, 1279, 1308,
                    1322, 1329, 1330, 1336, 1341, 1347, 1349, 1359, 1367, 1368, 1390, 1413, 1414, 1437, 1441, 1462, 1465, 1480,
                    1486, 1504, 1509, 1514, 1522, 1528, 1552, 1555, 1571, 1578, 1585, 1588, 1603, 1609, 1617, 1620, 1629, 1636,
                    1648, 1667, 1669, 1682, 1697, 1704, 1709, 1727, 1730, 1732, 1741, 1744, 1759, 1765, 1766, 1778, 1780, 1783,
                    1797, 1807, 1821, 1832, 1835, 1849, 1850, 1863, 1867, 1869, 1872, 1887, 1888, 1897, 1906, 1917, 1927, 1939,
                    1941, 1948, 1970, 1979, 1982, 2002, 2020, 2024, 2032, 2053, 2060, 2063, 2066, 2075, 2078, 2081, 2091, 2096,
                    2102, 2106, 2116, 2120, 2125, 2134, 2178, 2180, 2187, 2189, 2190, 2208, 2214, 2237, 2252, 2257, 2260, 2264,
                    2270, 2279, 2288, 2305, 2312, 2317, 2323, 2329, 2335, 2342, 2345, 2365, 2371, 2378, 2385, 2395, 2404, 2414,
                    2426, 2428, 2441, 2456, 2466, 2468, 2475, 2489, 2495, 2497, 2510, 2512, 2522, 2533, 2543, 2551, 2552, 2557,
                    2567, 2581, 2586, 2597, 2601, 2622, 2633, 2636, 2642, 2644, 2653, 2667, 2669, 2672, 2675, 2682, 2687, 2691,
                    2698, 2708, 2712, 2718, 2722, 2736, 2741, 2756, 2765, 2774, 2799, 2804, 2825, 2826, 2840, 2843, 2846, 2849,
                    2867, 2876, 2891, 2902, 2912, 2917, 2927, 2941, 2965, 2970, 2982, 2993, 3018, 3023, 3025, 3026, 3028, 3032,
                    3053, 3054, 3065, 3071, 3076, 3088, 3107, 3146, 3148, 3159, 3165, 3170, 3179, 3182, 3205, 3212, 3218, 3220,
                    3223, 3227, 3233, 3234, 3254, 3263, 3268, 3272, 3277, 3292, 3295, 3296, 3301, 3306, 3313, 3346, 3367, 3371,
                    3373, 3374, 3376, 3382, 3388, 3391, 3403, 3406, 3413, 3420, 3427, 3441, 3453, 3464, 3469, 3481, 3488, 3497,
                    3503, 3511, 3515, 3525, 3529, 3547, 3550, 3573, 3583, 3594, 3607, 3613, 3624, 3630, 3635, 3642, 3644, 3650,
                    3679, 3690, 3698, 3704, 3707, 3713, 3754, 3756, 3761, 3776, 3781, 3788, 3791, 3794, 3806, 3841, 3848, 3851,
                    3856, 3868, 3875, 3882, 3884, 3889, 3892, 3919, 3921, 3958, 3961, 3973, 3977, 3985, 3991, 4004, 4007, 4019,
                    4045, 4054, 4057, 4058, 4070, 4101, 4113, 4114, 4119, 4129, 4141, 4142, 4147, 4149, 4150, 4164, 4168, 4186,
                    4198, 4207, 4221, 4225, 4231, 4238, 4243, 4249, 4250, 4252, 4259, 4279, 4285, 4288, 4303, 4312, 4322, 4327,
                    4336, 4359, 4384, 4387, 4401, 4407, 4428, 4436, 4450, 4452, 4459, 4461, 4470, 4483, 4485, 4486, 4492, 4497,
                    4514, 4533, 4537, 4546, 4551, 4555, 4560, 4569, 4576, 4582, 4586, 4609, 4610, 4612, 4649, 4652, 4672, 4677,
                    4684, 4690, 4695, 4696, 4702, 4705, 4717, 4726, 4736, 4753, 4754, 4756, 4775, 4801, 4819, 4828, 4838, 4852,
                    4856, 4873, 4887, 4891, 4904, 4912, 4921, 4936, 4941, 4942, 4963, 4984, 4990, 5005, 5013, 5020, 5039, 5048,
                    5062, 5080, 5085, 5086, 5095, 5096, 5102, 5107, 5141, 5145, 5148, 5157, 5158, 5162, 5172, 5182, 5184, 5193,
                    5199, 5201, 5204, 5211, 5223, 5230, 5232, 5253, 5257, 5260, 5284, 5306, 5331, 5334, 5364, 5367, 5371, 5382,
                    5386, 5399, 5400, 5409, 5415, 5416, 5424, 5436, 5454, 5462, 5468, 5481, 5505, 5511, 5518, 5530, 5532, 5539,
                    5553, 5573, 5580, 5597, 5602, 5608, 5611, 5613, 5625, 5632, 5635, 5638, 5652, 5659, 5677, 5686, 5689, 5698,
                    5703, 5707, 5731, 5738, 5743, 5748, 5758, 5762, 5768, 5773, 5776, 5815, 5841, 5847, 5860, 5870, 5875, 5877,
                    5884, 5892, 5902, 5910, 5916, 5919, 5935, 5937, 5944, 5947, 5958, 5962, 5969, 5981, 5995, 5998, 6003, 6010,
                    6016, 6025, 6031, 6036, 6039, 6045, 6049, 6052, 6067, 6074, 6087, 6101, 6121, 6129, 6142, 6151, 6157, 6166,
                    6170, 6175, 6186, 6203, 6217, 6231, 6241, 6242, 6248, 6256, 6265, 6275, 6277, 6302, 6315, 6318, 6330, 6343,
                    6347, 6350, 6352, 6371, 6383, 6386, 6405, 6409, 6410, 6433, 6436, 6439, 6463, 6468, 6477, 6496, 6511, 6516,
                    6519, 6523, 6530, 6539, 6547, 6589, 6592, 6601, 6610, 6616, 6619, 6626, 6635, 6637, 6638, 6652, 6656, 6662,
                    6689, 6699, 6710, 6714, 6719, 6739, 6751, 6775, 6779, 6788, 6798, 6815, 6819, 6825, 6826, 6836, 6843, 6845,
                    6858, 6868, 6877, 6881, 6891, 6896, 6899, 6901, 6908, 6914, 6916, 6923, 6925, 6928, 6931, 6934, 6937, 6938,
                    6949, 6956, 6973, 6976, 6981, 6988, 6999, 7016, 7027, 7029, 7055, 7058, 7074, 7083, 7093, 7100, 7105, 7112,
                    7118, 7120, 7129, 7166, 7207, 7216, 7226, 7234, 7236, 7246, 7248, 7254, 7263, 7273, 7274, 7293, 7297, 7310,
                    7315, 7317, 7331, 7338, 7340, 7346, 7355, 7366, 7383, 7389, 7393, 7396, 7400, 7414, 7417, 7426, 7432, 7452,
                    7468, 7488, 7491, 7494, 7497, 7515, 7531, 7552, 7562, 7564, 7569, 7582, 7585, 7588, 7592, 7597, 7603, 7610,
                    7624, 7642, 7647, 7653, 7654, 7666, 7668, 7684, 7705, 7732, 7741, 7749, 7750, 7759, 7764, 7777, 7790, 7817,
                    7826, 7831, 7859, 7871, 7873, 7876, 7900, 7904, 7913, 7916, 7922, 7946, 7953, 7956, 7963, 7979, 7981, 7984,
                    7989, 7999, 8001, 8021, 8056, 8080, 8083, 8089, 8090, 8114, 8128, 8133, 8145, 8155, 8161, 8182, 8186, 8191,
                    8192, 8195, 8201, 8207, 8210, 8228, 8249, 8252, 8258, 8267, 8270, 8275, 8284, 8293, 8298, 8305, 8317, 8328,
                    8336, 8345, 8351, 8367, 8379, 8381, 8382, 8393, 8402, 8414, 8417, 8420, 8429, 8435, 8438, 8450, 8452, 8459,
                    8470, 8486, 8490, 8500, 8524, 8536, 8552, 8558, 8570, 8576, 8582, 8594, 8606, 8619, 8621, 8624, 8633, 8641,
                    8653, 8654, 8662, 8675, 8689, 8695, 8702, 8706, 8720, 8729, 8739, 8741, 8751, 8759, 8766, 8777, 8783, 8786,
                    8795, 8802, 8814, 8821, 8828, 8831, 8837, 8842, 8855, 8856, 8868, 8877, 8890, 8892, 8900, 8909, 8912, 8921,
                    8922, 8927, 8943, 8948, 8951, 8958, 8984, 8994, 9000, 9013, 9018, 9020, 9031, 9035, 9045, 9052, 9056, 9059,
                    9066, 9076, 9089, 9129, 9132, 9147, 9164, 9167, 9185, 9195, 9197, 9210, 9217, 9229, 9248, 9254, 9263, 9266,
                    9268, 9290, 9310, 9316, 9338, 9340, 9343, 9344, 9350, 9354, 9359, 9361, 9364, 9368, 9371, 9373, 9389, 9390,
                    9409, 9443, 9445, 9446, 9452, 9490, 9492, 9495, 9508, 9523, 9543, 9558, 9567, 9580, 9585, 9591, 9611, 9613,
                    9614, 9621, 9631, 9642, 9656, 9661, 9667, 9694, 9715, 9722, 9738, 9745, 9758, 9764, 9782, 9791, 9793, 9794,
                    9805, 9808, 9811, 9817, 9824, 9836, 9851, 9853, 9854, 9877, 9881, 9923, 9935, 9937, 9954, 9959, 9963, 9968,
                    9973, 9974, 9980, 9983, 9985, 10003, 10010, 10034, 10040, 10063, 10077, 10081, 10082, 10084, 10088, 10091,
                    10105, 10111, 10118, 10127, 10135, 10169, 10172, 10183, 10190, 10192, 10211, 10218, 10228, 10235, 10242, 10244,
                    10271, 10278, 10296, 10299, 10307, 10309, 10310, 10314, 10316, 10321, 10328, 10338, 10343, 10344, 10347, 10352,
                    10364, 10373, 10386, 10391, 10398, 10408, 10413, 10422, 10445, 10460, 10463, 10464, 10474, 10476, 10487, 10494,
                    10499, 10506, 10513, 10516, 10523, 10539, 10549, 10550, 10567, 10576, 10625, 10635, 10643, 10656, 10671, 10676,
                    10683, 10685, 10688, 10697, 10700, 10712, 10724, 10728, 10734, 10739, 10762, 10772, 10781, 10800, 10805, 10827,
                    10830, 10837, 10841, 10847, 10848, 10857, 10866, 10868, 10872, 10887, 10899, 10901, 10915, 10924, 10929, 10942,
                    10971, 10992, 10995, 11002, 11010, 11027, 11029, 11045, 11057, 11070, 11092, 11099, 11106, 11115, 11120, 11126,
                    11153, 11190, 11199, 11222, 11225, 11226, 11231, 11244, 11259, 11261, 11270, 11273, 11300, 11307, 11318, 11332,
                    11335, 11341, 11347, 11354, 11360, 11369, 11372, 11378, 11396, 11405, 11417, 11424, 11427, 11430, 11439, 11442,
                    11448, 11461, 11468, 11471, 11473, 11476, 11480, 11489, 11499, 11516, 11522, 11531, 11539, 11546, 11551, 11564,
                    11582, 11589, 11593, 11601, 11608, 11617, 11630, 11663, 11666, 11668, 11677, 11682, 11687, 11696, 11713, 11728,
                    11747, 11750, 11754, 11759, 11761, 11764, 11767, 11797, 11804, 11808, 11831, 11832, 11849, 11855, 11863, 11880,
                    11883, 11885, 11898, 11916, 11924, 11928, 11947, 11955, 11961, 11962, 11982, 11990, 12010, 12018, 12027, 12029,
                    12035, 12055, 12062, 12068, 12071, 12072, 12086, 12097, 12098, 12100, 12112, 12118, 12133, 12134, 12137, 12161,
                    12162, 12171, 12174, 12185, 12204, 12212, 12215, 12216, 12253, 12260, 12277, 12289, 12295, 12314, 12323, 12325,
                    12335, 12349, 12358, 12372, 12376, 12379, 12386, 12395, 12416, 12421, 12440, 12452, 12455, 12456, 12462, 12467,
                    12479, 12505, 12521, 12535, 12547, 12556, 12561, 12568, 12578, 12583, 12595, 12604, 12610, 12621, 12622, 12624,
                    12629, 12630, 12639, 12640, 12650, 12679, 12680, 12698, 12716, 12721, 12722, 12731, 12742, 12745, 12751, 12759,
                    12763, 12769, 12781, 12793, 12799, 12815, 12824, 12836, 12845, 12853, 12854, 12857, 12866, 12872, 12875, 12878,
                    12880, 12885, 12901, 12902, 12920, 12926, 12929, 12939, 12941, 12950, 12953, 12992, 13002, 13010, 13058, 13072,
                    13084, 13087, 13091, 13097, 13108, 13123, 13149, 13150, 13163, 13166, 13178, 13180, 13184, 13204, 13238, 13242,
                    13249, 13255, 13269, 13270, 13274, 13285, 13289, 13298, 13307, 13312, 13335, 13345, 13357, 13366, 13372, 13380,
                    13384, 13395, 13407, 13408, 13413, 13414, 13417, 13437, 13441, 13447, 13456, 13459, 13461, 13466, 13484, 13487,
                    13489, 13492, 13496, 13510, 13531, 13538, 13543, 13547, 13572, 13581, 13590, 13605, 13612, 13624, 13630, 13632,
                    13638, 13661, 13665, 13668, 13686, 13699, 13701, 13708, 13716, 13725, 13730, 13742, 13747, 13749, 13753, 13754,
                    13771, 13773, 13782, 13785, 13798, 13802, 13809, 13828, 13832, 13840, 13850, 13861, 13874, 13876, 13886, 13897,
                    13900, 13915, 13927, 13951, 13955, 13962, 13969, 13979, 13988, 13998, 14000, 14005, 14027, 14037, 14051, 14054,
                    14060, 14063, 14071, 14078, 14080, 14085, 14086, 14095, 14138, 14145, 14158, 14166, 14179, 14181, 14188, 14193,
                    14200, 14203, 14215, 14224, 14230, 14233, 14236, 14246, 14267, 14282, 14287, 14301, 14323, 14325, 14329, 14339,
                    14342, 14351, 14356, 14359, 14370, 14402, 14411, 14421, 14431, 14435, 14442, 14447, 14456, 14459, 14472, 14477,
                    14490, 14496, 14501, 14505, 14513, 14520, 14534, 14562, 14576, 14579, 14585, 14586, 14606, 14627, 14630, 14634,
                    14636, 14647, 14653, 14659, 14671, 14674, 14701, 14716, 14719, 14720, 14725, 14730, 14743, 14747, 14786, 14788,
                    14792, 14795, 14809, 14831, 14834, 14850, 14855, 14859, 14864, 14876, 14889, 14890, 14898, 14909, 14917, 14924,
                    14929, 14942, 14951, 14969, 14970, 14972, 14982, 14988, 14994, 15005, 15016, 15024, 15030, 15048, 15054, 15066,
                    15071, 15072, 15075, 15119, 15121, 15133, 15138, 15143, 15170, 15194, 15212, 15223, 15229, 15254, 15263, 15270,
                    15273, 15287, 15299, 15301, 15306, 15313, 15320, 15323, 15329, 15332, 15341, 15359, 15361, 15364, 15367, 15379,
                    15391, 15415, 15416, 15419, 15433, 15469, 15478, 15481, 15482, 15498, 15505, 15517, 15518, 15527, 15528, 15545,
                    15548, 15554, 15565, 15577, 15580, 15587, 15601, 15604, 15611, 15616, 15619, 15625, 15633, 15674, 15681, 15691,
                    15693, 15696, 15712, 15717, 15724, 15727, 15730, 15746, 15751, 15760, 15770, 15782, 15791, 15796, 15808, 15814,
                    15825, 15828, 15835, 15844, 15847, 15853, 15856, 15877, 15878, 15887, 15908, 15917, 15923, 15930, 15937, 15958,
                    15977, 15991, 16007, 16011, 16014, 16021, 16022, 16028, 16035, 16041, 16056, 16069, 16076, 16081, 16087, 16088,
                    16110, 16112, 16117, 16150, 16153, 16160, 16166, 16172, 16190, 16195, 16204, 16216, 16222, 16228, 16245, 16255,
                    16265, 16273, 16276, 16283, 16295, 16304, 16313, 16319, 16328, 16345, 16355, 16364, 16372, 16375, 16382, -1 };
            primitivePolynomials[14] = PrimitivePolynomialDegree15;
        }
    }

    private void applyPrimitivePolynomialDegree15() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_15) {
            final long[] PrimitivePolynomialDegree16 = { 22, 28, 31, 41, 94, 107, 151, 158, 167, 174, 203, 208, 214, 223, 227, 266,
                    268, 274, 279, 302, 310, 322, 328, 336, 370, 398, 421, 436, 440, 451, 454, 463, 465, 494, 508, 532, 555, 563,
                    577, 580, 584, 607, 608, 665, 675, 692, 707, 737, 750, 757, 800, 805, 809, 837, 865, 949, 950, 956, 961, 1016,
                    1030, 1072, 1119, 1130, 1135, 1137, 1144, 1149, 1180, 1214, 1221, 1234, 1239, 1249, 1250, 1267, 1273, 1342,
                    1344, 1373, 1378, 1408, 1417, 1418, 1448, 1454, 1510, 1513, 1522, 1543, 1550, 1552, 1588, 1592, 1597, 1606,
                    1610, 1617, 1623, 1657, 1697, 1729, 1735, 1769, 1778, 1826, 1858, 1870, 1875, 1922, 1924, 1933, 1972, 1979,
                    1987, 1994, 2008, 2023, 2029, 2053, 2081, 2087, 2094, 2111, 2113, 2114, 2123, 2190, 2231, 2276, 2285, 2297,
                    2336, 2345, 2353, 2363, 2368, 2373, 2391, 2402, 2413, 2422, 2425, 2461, 2462, 2490, 2492, 2510, 2564, 2573,
                    2598, 2627, 2633, 2647, 2660, 2664, 2678, 2684, 2691, 2759, 2768, 2773, 2794, 2813, 2831, 2843, 2846, 2849,
                    2856, 2893, 2901, 2924, 2942, 2975, 2979, 2985, 3020, 3025, 3028, 3051, 3127, 3142, 3145, 3156, 3165, 3196,
                    3199, 3217, 3218, 3253, 3258, 3260, 3289, 3314, 3326, 3331, 3371, 3381, 3396, 3441, 3442, 3460, 3477, 3491,
                    3493, 3543, 3549, 3550, 3553, 3563, 3568, 3604, 3632, 3650, 3662, 3674, 3685, 3686, 3700, 3703, 3704, 3723,
                    3743, 3753, 3756, 3759, 3762, 3771, 3776, 3779, 3839, 3856, 3871, 3887, 3896, 3901, 3916, 3919, 3937, 3943,
                    3955, 3961, 3988, 3997, 4011, 4026, 4033, 4045, 4060, 4063, 4064, 4126, 4136, 4167, 4173, 4188, 4195, 4226,
                    4291, 4298, 4308, 4312, 4336, 4371, 4378, 4384, 4393, 4421, 4425, 4439, 4440, 4500, 4514, 4523, 4534, 4593,
                    4615, 4630, 4636, 4645, 4684, 4687, 4723, 4735, 4746, 4779, 4782, 4793, 4796, 4813, 4850, 4867, 4874, 4881,
                    4894, 4904, 4927, 4929, 4989, 5000, 5020, 5036, 5041, 5059, 5074, 5090, 5110, 5134, 5152, 5176, 5181, 5204,
                    5218, 5242, 5253, 5260, 5281, 5282, 5313, 5316, 5326, 5340, 5347, 5354, 5368, 5394, 5396, 5400, 5405, 5439,
                    5442, 5459, 5478, 5484, 5508, 5523, 5545, 5565, 5566, 5580, 5608, 5613, 5647, 5661, 5671, 5678, 5680, 5685,
                    5689, 5692, 5724, 5745, 5755, 5757, 5786, 5792, 5810, 5824, 5869, 5872, 5895, 5923, 5925, 5926, 5944, 5986,
                    6012, 6015, 6046, 6049, 6061, 6067, 6099, 6121, 6155, 6163, 6203, 6208, 6231, 6241, 6254, 6262, 6266, 6275,
                    6278, 6292, 6329, 6355, 6357, 6374, 6380, 6423, 6427, 6430, 6436, 6443, 6445, 6458, 6478, 6490, 6508, 6519,
                    6520, 6530, 6541, 6556, 6607, 6612, 6626, 6632, 6649, 6666, 6674, 6733, 6782, 6786, 6798, 6821, 6840, 6846,
                    6865, 6884, 6891, 6925, 6938, 6967, 6988, 6996, 7009, 7016, 7030, 7043, 7045, 7052, 7073, 7142, 7153, 7168,
                    7174, 7183, 7195, 7204, 7214, 7222, 7267, 7269, 7279, 7293, 7312, 7327, 7334, 7337, 7343, 7346, 7372, 7387,
                    7390, 7396, 7423, 7428, 7437, 7466, 7474, 7476, 7483, 7518, 7527, 7545, 7572, 7586, 7597, 7630, 7653, 7657,
                    7671, 7672, 7715, 7717, 7732, 7735, 7753, 7811, 7817, 7825, 7832, 7838, 7841, 7844, 7859, 7861, 7873, 7880,
                    7897, 7904, 7910, 7960, 7969, 7970, 7976, 7979, 8007, 8041, 8047, 8052, 8061, 8078, 8128, 8146, 8162, 8250,
                    8270, 8294, 8324, 8351, 8357, 8361, 8364, 8393, 8396, 8407, 8413, 8414, 8432, 8435, 8441, 8447, 8449, 8456,
                    8485, 8504, 8512, 8532, 8551, 8560, 8566, 8581, 8609, 8639, 8641, 8671, 8695, 8701, 8711, 8739, 8763, 8778,
                    8783, 8785, 8797, 8802, 8816, 8828, 8837, 8852, 8859, 8889, 8900, 8903, 8909, 8910, 8922, 8924, 8955, 8958,
                    8980, 8994, 9006, 9017, 9032, 9040, 9061, 9066, 9071, 9076, 9086, 9104, 9150, 9161, 9164, 9185, 9188, 9212,
                    9230, 9242, 9247, 9260, 9280, 9300, 9313, 9325, 9343, 9349, 9354, 9367, 9368, 9455, 9458, 9469, 9481, 9482,
                    9495, 9526, 9530, 9558, 9562, 9573, 9583, 9595, 9597, 9616, 9638, 9649, 9662, 9691, 9700, 9703, 9710, 9721,
                    9724, 9727, 9743, 9779, 9785, 9811, 9820, 9827, 9851, 9854, 9863, 9884, 9894, 9903, 9908, 9912, 9925, 9926,
                    9971, 9973, 9977, 10021, 10039, 10048, 10051, 10065, 10071, 10072, 10101, 10106, 10130, 10136, 10148, 10155,
                    10157, 10160, 10166, 10183, 10192, 10225, 10241, 10247, 10284, 10304, 10322, 10331, 10333, 10340, 10347, 10357,
                    10371, 10386, 10404, 10414, 10422, 10448, 10451, 10473, 10479, 10501, 10530, 10542, 10544, 10571, 10628, 10643,
                    10673, 10683, 10736, 10795, 10797, 10815, 10817, 10842, 10844, 10863, 10899, 10902, 10917, 10953, 10967, 11002,
                    11024, 11029, 11030, 11043, 11087, 11106, 11130, 11156, 11176, 11221, 11267, 11282, 11294, 11332, 11353, 11369,
                    11372, 11375, 11413, 11429, 11430, 11444, 11462, 11465, 11471, 11473, 11495, 11499, 11519, 11562, 11576, 11582,
                    11596, 11602, 11611, 11627, 11682, 11687, 11691, 11733, 11747, 11759, 11778, 11852, 11857, 11858, 11893, 11907,
                    11928, 11931, 11933, 11967, 11976, 11989, 12003, 12024, 12030, 12037, 12044, 12052, 12059, 12075, 12083, 12117,
                    12138, 12146, 12202, 12230, 12254, 12304, 12316, 12337, 12347, 12367, 12398, 12421, 12428, 12446, 12449, 12467,
                    12479, 12502, 12521, 12553, 12568, 12573, 12592, 12597, 12601, 12615, 12619, 12694, 12697, 12698, 12713, 12722,
                    12733, 12736, 12790, 12794, 12803, 12815, 12829, 12833, 12840, 12875, 12877, 12916, 12923, 12935, 12949, 12954,
                    12956, 12959, 12978, 13001, 13010, 13043, 13049, 13058, 13112, 13140, 13149, 13163, 13187, 13196, 13237, 13244,
                    13264, 13279, 13292, 13295, 13307, 13312, 13317, 13327, 13348, 13390, 13413, 13417, 13418, 13451, 13475, 13482,
                    13510, 13527, 13543, 13547, 13627, 13650, 13683, 13696, 13702, 13713, 13739, 13749, 13767, 13774, 13781, 13795,
                    13821, 13825, 13838, 13852, 13879, 13888, 13897, 13906, 13939, 13962, 13981, 13985, 14020, 14030, 14041, 14047,
                    14048, 14066, 14092, 14120, 14126, 14131, 14143, 14146, 14152, 14155, 14157, 14188, 14206, 14243, 14278, 14308,
                    14317, 14335, 14354, 14356, 14379, 14381, 14384, 14389, 14390, 14414, 14425, 14462, 14472, 14508, 14511, 14519,
                    14528, 14561, 14574, 14588, 14594, 14606, 14614, 14639, 14641, 14648, 14668, 14673, 14679, 14695, 14702, 14704,
                    14740, 14754, 14774, 14792, 14797, 14806, 14812, 14856, 14876, 14900, 14910, 14912, 14915, 14922, 14939, 14946,
                    14955, 14966, 14976, 14986, 14993, 15012, 15041, 15053, 15056, 15059, 15110, 15116, 15133, 15134, 15137, 15147,
                    15182, 15203, 15215, 15245, 15246, 15264, 15269, 15296, 15314, 15323, 15364, 15386, 15391, 15436, 15460, 15464,
                    15469, 15470, 15477, 15488, 15494, 15548, 15551, 15560, 15563, 15604, 15607, 15616, 15631, 15699, 15701, 15708,
                    15739, 15746, 15772, 15793, 15832, 15837, 15866, 15899, 15911, 15918, 15952, 15962, 15967, 15973, 15977, 16016,
                    16035, 16044, 16067, 16082, 16084, 16098, 16107, 16142, 16149, 16165, 16172, 16178, 16197, 16201, 16215, 16221,
                    16226, 16232, 16246, 16261, 16279, 16280, 16290, 16314, 16345, 16355, 16361, 16393, 16394, 16438, 16450, 16507,
                    16523, 16533, 16603, 16648, 16653, 16672, 16682, 16709, 16713, 16719, 16733, 16740, 16761, 16767, 16771, 16788,
                    16811, 16814, 16816, 16828, 16834, 16863, 16864, 16879, 16888, 16904, 16909, 16921, 16934, 16951, 16983, 16999,
                    17000, 17006, 17020, 17030, 17033, 17044, 17051, 17072, 17078, 17084, 17087, 17090, 17123, 17132, 17140, 17149,
                    17157, 17164, 17170, 17179, 17237, 17248, 17260, 17272, 17275, 17287, 17294, 17296, 17305, 17312, 17321, 17367,
                    17368, 17378, 17433, 17455, 17457, 17508, 17559, 17560, 17582, 17596, 17599, 17613, 17619, 17622, 17655, 17661,
                    17698, 17712, 17715, 17721, 17722, 17742, 17778, 17818, 17836, 17841, 17856, 17883, 17896, 17901, 17926, 17937,
                    17992, 17995, 17998, 18021, 18039, 18067, 18122, 18129, 18130, 18142, 18148, 18163, 18192, 18211, 18228, 18264,
                    18347, 18372, 18409, 18412, 18418, 18423, 18433, 18439, 18445, 18451, 18467, 18502, 18514, 18547, 18575, 18593,
                    18613, 18620, 18637, 18640, 18712, 18728, 18742, 18748, 18753, 18756, 18771, 18814, 18818, 18827, 18835, 18842,
                    18854, 18858, 18895, 18914, 18925, 18933, 18937, 18944, 18973, 19001, 19012, 19016, 19027, 19040, 19074, 19085,
                    19093, 19100, 19107, 19128, 19141, 19142, 19156, 19169, 19176, 19222, 19226, 19247, 19259, 19262, 19291, 19309,
                    19324, 19334, 19338, 19343, 19376, 19408, 19413, 19420, 19441, 19444, 19454, 19461, 19473, 19479, 19489, 19591,
                    19605, 19616, 19619, 19653, 19654, 19681, 19711, 19719, 19726, 19738, 19767, 19819, 19864, 19867, 19885, 19894,
                    19900, 19903, 19941, 19951, 19959, 19966, 20009, 20018, 20020, 20038, 20049, 20066, 20108, 20130, 20132, 20167,
                    20219, 20227, 20263, 20267, 20272, 20289, 20296, 20307, 20316, 20323, 20335, 20344, 20354, 20360, 20368, 20390,
                    20402, 20413, 20425, 20428, 20434, 20443, 20488, 20502, 20505, 20535, 20541, 20554, 20577, 20583, 20602, 20611,
                    20614, 20626, 20628, 20635, 20642, 20674, 20716, 20734, 20765, 20801, 20804, 20808, 20831, 20832, 20862, 20877,
                    20880, 20885, 20889, 20905, 20914, 20967, 21028, 21031, 21043, 21052, 21058, 21087, 21088, 21093, 21106, 21108,
                    21118, 21122, 21131, 21139, 21141, 21146, 21162, 21164, 21184, 21208, 21211, 21213, 21230, 21270, 21276, 21285,
                    21297, 21304, 21310, 21330, 21339, 21346, 21391, 21427, 21451, 21465, 21468, 21471, 21533, 21610, 21615, 21630,
                    21633, 21657, 21658, 21669, 21670, 21673, 21701, 21719, 21720, 21747, 21819, 21839, 21854, 21857, 21864, 21882,
                    21900, 21903, 21928, 21931, 21946, 21971, 21974, 21978, 21993, 21994, 22030, 22035, 22063, 22068, 22086, 22104,
                    22119, 22153, 22168, 22183, 22189, 22204, 22230, 22240, 22246, 22269, 22282, 22302, 22330, 22347, 22349, 22383,
                    22386, 22432, 22450, 22473, 22487, 22527, 22537, 22557, 22561, 22568, 22573, 22591, 22593, 22605, 22620, 22627,
                    22636, 22647, 22697, 22715, 22725, 22732, 22749, 22766, 22768, 22771, 22788, 22797, 22822, 22828, 22851, 22888,
                    22893, 22901, 22902, 22921, 22929, 22946, 22948, 22951, 22958, 22975, 22983, 22990, 23032, 23047, 23053, 23054,
                    23082, 23095, 23116, 23197, 23221, 23257, 23260, 23263, 23267, 23282, 23284, 23314, 23326, 23335, 23368, 23373,
                    23401, 23415, 23432, 23476, 23479, 23497, 23505, 23508, 23531, 23536, 23542, 23565, 23573, 23590, 23593, 23604,
                    23621, 23622, 23636, 23662, 23667, 23703, 23725, 23734, 23738, 23752, 23800, 23803, 23818, 23832, 23842, 23851,
                    23873, 23883, 23888, 23910, 23934, 23938, 23949, 23955, 23967, 23973, 23991, 24024, 24030, 24039, 24067, 24069,
                    24084, 24112, 24115, 24184, 24187, 24262, 24276, 24279, 24313, 24331, 24336, 24348, 24367, 24372, 24402, 24420,
                    24444, 24465, 24466, 24471, 24475, 24481, 24488, 24501, 24514, 24531, 24534, 24559, 24602, 24614, 24637, 24664,
                    24676, 24679, 24697, 24709, 24714, 24716, 24731, 24744, 24762, 24764, 24769, 24806, 24812, 24872, 24878, 24883,
                    24909, 24943, 24946, 24964, 24992, 25016, 25022, 25024, 25039, 25051, 25060, 25072, 25097, 25122, 25148, 25194,
                    25201, 25204, 25238, 25241, 25248, 25257, 25275, 25278, 25304, 25307, 25320, 25334, 25348, 25357, 25372, 25394,
                    25454, 25465, 25472, 25492, 25505, 25517, 25530, 25558, 25562, 25598, 25609, 25612, 25627, 25651, 25653, 25668,
                    25711, 25765, 25770, 25775, 25777, 25783, 25801, 25802, 25812, 25821, 25897, 25906, 25929, 25940, 25950, 25968,
                    25971, 25977, 25994, 25996, 25999, 26001, 26030, 26069, 26100, 26109, 26131, 26144, 26149, 26162, 26167, 26173,
                    26179, 26193, 26230, 26234, 26246, 26267, 26283, 26300, 26341, 26356, 26377, 26397, 26404, 26411, 26422, 26451,
                    26454, 26463, 26488, 26498, 26509, 26512, 26517, 26534, 26545, 26546, 26636, 26749, 26753, 26759, 26774, 26789,
                    26808, 26825, 26831, 26859, 26888, 26918, 26929, 26950, 26954, 26973, 26997, 26998, 27008, 27038, 27109, 27127,
                    27150, 27168, 27178, 27212, 27224, 27229, 27246, 27251, 27257, 27258, 27260, 27287, 27300, 27315, 27329, 27332,
                    27349, 27350, 27354, 27359, 27377, 27378, 27383, 27384, 27387, 27392, 27402, 27438, 27481, 27482, 27494, 27531,
                    27542, 27546, 27564, 27567, 27582, 27608, 27611, 27624, 27629, 27655, 27667, 27676, 27710, 27724, 27745, 27752,
                    27770, 27779, 27781, 27791, 27809, 27810, 27815, 27827, 27834, 27865, 27899, 27901, 27914, 27950, 27994, 28005,
                    28009, 28033, 28039, 28060, 28067, 28069, 28099, 28113, 28114, 28139, 28142, 28153, 28166, 28172, 28183, 28194,
                    28232, 28245, 28246, 28252, 28265, 28301, 28323, 28344, 28362, 28400, 28417, 28454, 28466, 28468, 28477, 28498,
                    28510, 28513, 28571, 28573, 28578, 28587, 28601, 28646, 28663, 28681, 28682, 28699, 28706, 28708, 28717, 28720,
                    28735, 28761, 28783, 28788, 28811, 28825, 28838, 28850, 28891, 28897, 28932, 28975, 28998, 29052, 29090, 29096,
                    29104, 29113, 29133, 29142, 29170, 29192, 29221, 29236, 29246, 29293, 29305, 29312, 29339, 29365, 29389, 29402,
                    29423, 29443, 29445, 29463, 29473, 29493, 29506, 29518, 29532, 29560, 29590, 29594, 29637, 29647, 29650, 29655,
                    29661, 29680, 29721, 29751, 29755, 29760, 29772, 29778, 29799, 29824, 29841, 29842, 29853, 29867, 29949, 29950,
                    29964, 29967, 29985, 29992, 30000, 30020, 30024, 30030, 30066, 30071, 30078, 30082, 30096, 30101, 30102, 30122,
                    30141, 30159, 30168, 30177, 30183, 30197, 30213, 30231, 30232, 30241, 30253, 30259, 30262, 30268, 30297, 30316,
                    30322, 30344, 30350, 30355, 30429, 30445, 30477, 30513, 30520, 30540, 30551, 30573, 30609, 30622, 30635, 30658,
                    30667, 30681, 30684, 30703, 30705, 30715, 30742, 30748, 30758, 30767, 30770, 30772, 30807, 30814, 30824, 30857,
                    30875, 30931, 30933, 30949, 30953, 30982, 31010, 31012, 31039, 31041, 31061, 31082, 31087, 31096, 31115, 31130,
                    31168, 31171, 31177, 31180, 31191, 31207, 31247, 31249, 31285, 31303, 31310, 31337, 31352, 31357, 31374, 31379,
                    31388, 31410, 31416, 31467, 31495, 31504, 31507, 31509, 31514, 31516, 31530, 31555, 31567, 31570, 31586, 31598,
                    31605, 31609, 31612, 31626, 31634, 31655, 31681, 31705, 31706, 31718, 31735, 31741, 31744, 31762, 31774, 31864,
                    31874, 31900, 31910, 31928, 31965, 31976, 32016, 32032, 32037, 32062, 32069, 32115, 32128, 32171, 32173, 32182,
                    32191, 32193, 32194, 32229, 32230, 32248, 32263, 32264, 32269, 32298, 32335, 32340, 32356, 32374, 32390, 32401,
                    32414, 32417, 32442, 32450, 32461, 32473, 32479, 32530, 32536, 32548, 32577, 32628, 32642, 32665, 32666, 32668,
                    32696, 32722, 32757, 32758, -1 };
            primitivePolynomials[15] = PrimitivePolynomialDegree16;
        }
    }

    private void applyPrimitivePolynomialDegree16() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_16) {
            final long[] PrimitivePolynomialDegree17 = { 4, 7, 16, 22, 25, 31, 32, 42, 52, 61, 70, 76, 81, 87, 93, 98, 122, 133,
                    134, 140, 146, 158, 171, 176, 179, 182, 191, 193, 224, 227, 229, 236, 248, 262, 273, 276, 280, 283, 290, 309,
                    316, 319, 321, 328, 346, 355, 367, 369, 372, 382, 388, 392, 395, 397, 403, 409, 410, 425, 443, 472, 475, 481,
                    488, 493, 501, 515, 522, 524, 527, 535, 542, 545, 555, 558, 560, 563, 570, 578, 583, 590, 597, 604, 608, 614,
                    635, 637, 653, 654, 659, 666, 671, 689, 690, 695, 713, 722, 733, 758, 770, 782, 784, 793, 803, 805, 812, 838,
                    849, 875, 877, 880, 892, 895, 899, 919, 920, 932, 935, 936, 941, 950, 961, 962, 971, 982, 986, 1012, 1021,
                    1024, 1029, 1030, 1051, 1054, 1064, 1067, 1082, 1096, 1116, 1119, 1129, 1130, 1132, 1137, 1147, 1150, 1154,
                    1165, 1166, 1174, 1177, 1184, 1194, 1204, 1208, 1213, 1219, 1233, 1264, 1267, 1281, 1312, 1321, 1330, 1332,
                    1335, 1341, 1356, 1371, 1377, 1380, 1384, 1395, 1397, 1411, 1414, 1426, 1432, 1435, 1437, 1442, 1454, 1468,
                    1473, 1488, 1491, 1509, 1524, 1533, 1555, 1567, 1571, 1586, 1592, 1595, 1600, 1606, 1627, 1648, 1651, 1654,
                    1667, 1669, 1674, 1687, 1698, 1710, 1717, 1722, 1735, 1741, 1749, 1750, 1769, 1775, 1777, 1778, 1792, 1797,
                    1802, 1822, 1825, 1831, 1838, 1852, 1855, 1860, 1867, 1869, 1875, 1882, 1903, 1908, 1917, 1927, 1941, 1945,
                    1948, 1962, 1975, 1976, 1987, 1993, 2004, 2020, 2029, 2030, 2038, 2041, 2048, 2054, 2057, 2058, 2068, 2072,
                    2075, 2077, 2082, 2084, 2087, 2101, 2111, 2113, 2119, 2147, 2154, 2161, 2164, 2177, 2178, 2184, 2198, 2201,
                    2213, 2217, 2228, 2240, 2245, 2250, 2263, 2267, 2285, 2286, 2291, 2298, 2306, 2325, 2329, 2332, 2335, 2339,
                    2346, 2351, 2353, 2363, 2378, 2383, 2391, 2401, 2408, 2414, 2419, 2428, 2441, 2444, 2461, 2480, 2483, 2486,
                    2489, 2490, 2518, 2527, 2540, 2558, 2567, 2571, 2574, 2579, 2585, 2615, 2639, 2641, 2644, 2663, 2667, 2669,
                    2672, 2687, 2700, 2705, 2724, 2728, 2733, 2741, 2748, 2751, 2756, 2771, 2774, 2793, 2796, 2804, 2814, 2822,
                    2845, 2846, 2850, 2855, 2856, 2867, 2891, 2894, 2902, 2908, 2951, 2970, 2972, 2975, 2976, 2981, 2986, 2999,
                    3000, 3006, 3014, 3028, 3031, 3035, 3037, 3053, 3059, 3065, 3080, 3091, 3094, 3109, 3110, 3113, 3116, 3136,
                    3139, 3141, 3154, 3160, 3169, 3182, 3187, 3189, 3190, 3203, 3217, 3229, 3236, 3248, 3257, 3278, 3283, 3289,
                    3292, 3302, 3316, 3328, 3333, 3337, 3340, 3368, 3371, 3376, 3385, 3386, 3393, 3399, 3405, 3414, 3433, 3436,
                    3448, 3467, 3469, 3472, 3477, 3484, 3494, 3505, 3515, 3523, 3530, 3543, 3559, 3568, 3577, 3578, 3594, 3601,
                    3607, 3608, 3620, 3629, 3644, 3656, 3664, 3670, 3680, 3685, 3686, 3695, 3698, 3703, 3710, 3714, 3726, 3731,
                    3733, 3737, 3756, 3759, 3767, 3776, 3785, 3793, 3812, 3815, 3824, 3844, 3859, 3866, 3872, 3884, 3889, 3899,
                    3902, 3909, 3913, 3933, 3938, 3949, 3952, 3980, 3991, 3992, 4002, 4008, 4011, 4016, 4034, 4036, 4063, 4067,
                    4073, 4082, 4091, 4093, 4101, 4113, 4120, 4125, 4126, 4139, 4142, 4144, 4147, 4153, 4154, 4164, 4174, 4182,
                    4185, 4188, 4191, 4195, 4219, 4225, 4228, 4232, 4246, 4255, 4274, 4283, 4286, 4306, 4311, 4317, 4321, 4324,
                    4331, 4333, 4359, 4360, 4368, 4373, 4389, 4394, 4413, 4422, 4431, 4436, 4440, 4445, 4452, 4462, 4469, 4470,
                    4473, 4479, 4480, 4483, 4489, 4503, 4519, 4520, 4525, 4526, 4533, 4552, 4581, 4585, 4591, 4594, 4596, 4599,
                    4612, 4621, 4630, 4636, 4640, 4649, 4650, 4660, 4677, 4687, 4696, 4701, 4705, 4706, 4711, 4720, 4723, 4732,
                    4736, 4742, 4748, 4754, 4759, 4769, 4787, 4807, 4814, 4837, 4867, 4873, 4879, 4884, 4898, 4907, 4910, 4918,
                    4924, 4953, 4956, 4965, 4966, 4970, 4972, 4983, 4989, 4994, 5000, 5005, 5014, 5018, 5023, 5039, 5053, 5054,
                    5061, 5065, 5080, 5083, 5107, 5119, 5146, 5151, 5152, 5155, 5169, 5170, 5176, 5179, 5182, 5189, 5193, 5196,
                    5204, 5207, 5211, 5220, 5258, 5265, 5271, 5277, 5278, 5282, 5296, 5299, 5319, 5328, 5343, 5353, 5364, 5368,
                    5379, 5381, 5399, 5415, 5422, 5441, 5451, 5456, 5462, 5490, 5495, 5501, 5502, 5505, 5512, 5523, 5529, 5548,
                    5551, 5553, 5565, 5568, 5577, 5578, 5583, 5585, 5588, 5613, 5614, 5616, 5622, 5631, 5637, 5638, 5668, 5675,
                    5683, 5695, 5703, 5710, 5715, 5727, 5738, 5752, 5767, 5773, 5776, 5781, 5791, 5792, 5795, 5798, 5801, 5810,
                    5812, 5836, 5839, 5841, 5867, 5870, 5877, 5881, 5899, 5914, 5925, 5929, 5943, 5949, 5962, 5969, 5976, 5988,
                    5997, 6006, 6010, 6016, 6022, 6026, 6031, 6040, 6043, 6050, 6059, 6079, 6099, 6101, 6105, 6108, 6124, 6132,
                    6145, 6146, 6151, 6152, 6158, 6160, 6163, 6165, 6170, 6182, 6196, 6199, 6200, 6205, 6226, 6228, 6237, 6247,
                    6251, 6253, 6256, 6268, 6272, 6275, 6281, 6289, 6335, 6338, 6355, 6362, 6373, 6386, 6388, 6391, 6397, 6405,
                    6406, 6410, 6417, 6443, 6457, 6468, 6475, 6486, 6489, 6495, 6499, 6505, 6511, 6520, 6529, 6542, 6547, 6550,
                    6554, 6556, 6560, 6569, 6572, 6590, 6592, 6601, 6610, 6632, 6635, 6640, 6643, 6649, 6659, 6662, 6666, 6690,
                    6692, 6704, 6713, 6728, 6731, 6734, 6746, 6755, 6757, 6764, 6772, 6792, 6797, 6821, 6822, 6831, 6834, 6851,
                    6858, 6860, 6871, 6875, 6884, 6888, 6894, 6919, 6940, 6950, 6959, 6968, 6981, 6988, 6996, 6999, 7015, 7019,
                    7022, 7040, 7045, 7049, 7055, 7073, 7076, 7085, 7091, 7103, 7112, 7117, 7118, 7123, 7126, 7154, 7180, 7192,
                    7195, 7207, 7208, 7214, 7222, 7234, 7254, 7258, 7264, 7279, 7282, 7293, 7297, 7322, 7324, 7351, 7363, 7380,
                    7384, 7389, 7396, 7413, 7417, 7431, 7443, 7445, 7450, 7456, 7466, 7468, 7474, 7479, 7486, 7497, 7508, 7515,
                    7533, 7542, 7546, 7551, 7569, 7572, 7595, 7603, 7605, 7629, 7632, 7638, 7648, 7654, 7663, 7675, 7693, 7705,
                    7717, 7724, 7727, 7735, 7761, 7767, 7783, 7784, 7798, 7802, 7811, 7817, 7823, 7832, 7837, 7842, 7853, 7854,
                    7856, 7861, 7862, 7873, 7874, 7886, 7914, 7927, 7936, 7951, 7963, 7966, 7976, 7993, 8001, 8004, 8013, 8021,
                    8026, 8028, 8031, 8035, 8042, 8071, 8085, 8092, 8102, 8105, 8114, 8123, 8131, 8140, 8145, 8157, 8158, 8185,
                    8186, 8188, 8192, 8212, 8219, 8221, 8235, 8237, 8249, 8260, 8264, 8270, 8282, 8291, 8293, 8297, 8298, 8305,
                    8306, 8311, 8318, 8327, 8345, 8379, 8390, 8394, 8414, 8417, 8432, 8437, 8441, 8449, 8456, 8473, 8489, 8495,
                    8522, 8524, 8529, 8545, 8557, 8565, 8572, 8576, 8582, 8594, 8629, 8636, 8668, 8678, 8701, 8702, 8708, 8712,
                    8745, 8754, 8759, 8763, 8766, 8780, 8783, 8786, 8798, 8804, 8819, 8826, 8835, 8838, 8856, 8862, 8871, 8875,
                    8890, 8892, 8898, 8927, 8928, 8945, 8952, 8955, 8965, 8972, 8977, 8990, 8996, 9025, 9037, 9040, 9049, 9062,
                    9068, 9076, 9079, 9099, 9107, 9114, 9123, 9126, 9137, 9149, 9150, 9155, 9161, 9162, 9167, 9172, 9186, 9188,
                    9191, 9198, 9200, 9205, 9206, 9215, 9227, 9232, 9241, 9244, 9251, 9254, 9275, 9283, 9285, 9303, 9304, 9313,
                    9314, 9320, 9328, 9333, 9337, 9338, 9340, 9353, 9356, 9364, 9373, 9374, 9378, 9380, 9389, 9395, 9412, 9422,
                    9439, 9450, 9452, 9482, 9487, 9489, 9499, 9501, 9508, 9515, 9518, 9520, 9526, 9537, 9552, 9571, 9588, 9613,
                    9619, 9622, 9637, 9652, 9655, 9661, 9664, 9669, 9681, 9682, 9688, 9697, 9700, 9715, 9722, 9727, 9734, 9740,
                    9743, 9745, 9757, 9761, 9762, 9767, 9768, 9774, 9786, 9796, 9820, 9823, 9839, 9844, 9851, 9853, 9863, 9864,
                    9877, 9884, 9888, 9897, 9915, 9925, 9949, 9954, 9960, 9965, 9978, 9986, 9992, 9995, 10005, 10026, 10031, 10040,
                    10051, 10057, 10072, 10096, 10102, 10105, 10115, 10124, 10139, 10145, 10148, 10157, 10158, 10163, 10180, 10187,
                    10198, 10208, 10214, 10220, 10237, 10238, 10241, 10244, 10251, 10253, 10256, 10259, 10266, 10284, 10290, 10331,
                    10334, 10350, 10355, 10357, 10367, 10368, 10377, 10388, 10407, 10421, 10434, 10439, 10440, 10443, 10446, 10457,
                    10469, 10476, 10479, 10481, 10494, 10501, 10505, 10516, 10532, 10541, 10547, 10550, 10591, 10597, 10602, 10610,
                    10619, 10631, 10646, 10655, 10665, 10673, 10674, 10680, 10693, 10700, 10721, 10727, 10746, 10748, 10757, 10761,
                    10770, 10776, 10782, 10791, 10792, 10805, 10810, 10832, 10835, 10841, 10842, 10847, 10853, 10860, 10871, 10878,
                    10881, 10888, 10894, 10901, 10915, 10918, 10922, 10936, 10954, 10968, 10971, 10973, 10978, 10998, 11009, 11021,
                    11029, 11030, 11034, 11050, 11063, 11064, 11077, 11078, 11105, 11106, 11126, 11129, 11135, 11151, 11159, 11165,
                    11176, 11179, 11182, 11189, 11204, 11216, 11232, 11235, 11242, 11250, 11264, 11273, 11282, 11288, 11291, 11293,
                    11318, 11321, 11329, 11336, 11339, 11342, 11356, 11383, 11389, 11405, 11411, 11423, 11424, 11430, 11444, 11447,
                    11459, 11465, 11473, 11479, 11501, 11510, 11514, 11522, 11527, 11533, 11536, 11548, 11551, 11552, 11555, 11572,
                    11587, 11601, 11613, 11614, 11618, 11624, 11630, 11663, 11682, 11684, 11702, 11705, 11713, 11731, 11734, 11740,
                    11747, 11754, 11761, 11762, 11787, 11792, 11814, 11823, 11825, 11837, 11838, 11846, 11855, 11864, 11876, 11885,
                    11904, 11909, 11913, 11916, 11940, 11943, 11972, 11981, 11990, 11993, 12000, 12005, 12015, 12020, 12038, 12042,
                    12056, 12065, 12066, 12072, 12080, 12083, 12090, 12092, 12103, 12109, 12112, 12117, 12121, 12137, 12143, 12145,
                    12148, 12168, 12174, 12188, 12191, 12192, 12201, 12224, 12230, 12239, 12242, 12251, 12258, 12264, 12310, 12319,
                    12323, 12325, 12332, 12343, 12344, 12352, 12370, 12388, 12395, 12403, 12409, 12410, 12415, 12419, 12426, 12439,
                    12445, 12459, 12464, 12474, 12484, 12491, 12501, 12505, 12508, 12511, 12521, 12522, 12530, 12544, 12547, 12561,
                    12571, 12580, 12597, 12607, 12609, 12610, 12616, 12621, 12624, 12639, 12645, 12652, 12655, 12660, 12667, 12686,
                    12688, 12697, 12700, 12707, 12710, 12719, 12739, 12742, 12746, 12754, 12765, 12772, 12784, 12789, 12800, 12805,
                    12809, 12815, 12827, 12830, 12833, 12840, 12851, 12868, 12871, 12886, 12899, 12923, 12926, 12932, 12935, 12939,
                    12942, 12953, 12959, 12960, 13002, 13004, 13016, 13021, 13035, 13038, 13052, 13058, 13069, 13082, 13093, 13094,
                    13100, 13115, 13125, 13132, 13143, 13144, 13153, 13160, 13165, 13173, 13174, 13187, 13190, 13204, 13218, 13247,
                    13250, 13262, 13267, 13274, 13304, 13309, 13315, 13317, 13324, 13332, 13342, 13346, 13355, 13358, 13360, 13369,
                    13372, 13398, 13404, 13407, 13414, 13426, 13432, 13466, 13481, 13490, 13496, 13504, 13516, 13527, 13533, 13537,
                    13552, 13561, 13567, 13582, 13587, 13589, 13593, 13596, 13615, 13617, 13623, 13641, 13647, 13650, 13659, 13671,
                    13677, 13678, 13680, 13685, 13689, 13701, 13706, 13720, 13729, 13735, 13736, 13756, 13759, 13761, 13771, 13782,
                    13786, 13798, 13810, 13819, 13826, 13831, 13837, 13846, 13856, 13862, 13866, 13885, 13891, 13893, 13905, 13908,
                    13912, 13917, 13918, 13946, 13948, 13964, 13970, 13975, 13979, 13997, 14000, 14006, 14020, 14023, 14024, 14029,
                    14035, 14044, 14047, 14058, 14066, 14075, 14080, 14095, 14100, 14114, 14116, 14123, 14134, 14143, 14151, 14160,
                    14163, 14179, 14181, 14193, 14209, 14210, 14224, 14245, 14249, 14255, 14267, 14270, 14277, 14305, 14308, 14312,
                    14325, 14332, 14345, 14354, 14372, 14387, 14390, 14402, 14408, 14413, 14431, 14438, 14447, 14455, 14461, 14466,
                    14480, 14490, 14492, 14508, 14513, 14516, 14519, 14525, 14534, 14537, 14543, 14545, 14552, 14562, 14571, 14574,
                    14582, 14611, 14614, 14620, 14633, 14641, 14648, 14671, 14674, 14689, 14690, 14692, 14699, 14710, 14719, 14730,
                    14732, 14743, 14744, 14750, 14759, 14763, 14768, 14773, 14777, 14786, 14795, 14800, 14812, 14816, 14836, 14840,
                    14856, 14859, 14870, 14873, 14879, 14880, 14889, 14892, 14895, 14900, 14903, 14910, 14912, 14942, 14948, 14957,
                    14963, 14975, 14985, 14993, 15003, 15010, 15022, 15039, 15041, 15047, 15048, 15056, 15066, 15068, 15075, 15077,
                    15082, 15096, 15102, 15116, 15122, 15127, 15133, 15137, 15138, 15149, 15152, 15155, 15158, 15187, 15189, 15190,
                    15196, 15199, 15205, 15212, 15236, 15243, 15246, 15260, 15267, 15274, 15279, 15281, 15282, 15284, 15291, 15293,
                    15302, 15319, 15329, 15336, 15347, 15353, 15361, 15371, 15397, 15404, 15407, 15421, 15433, 15441, 15442, 15463,
                    15472, 15478, 15488, 15493, 15498, 15511, 15521, 15534, 15539, 15541, 15560, 15563, 15574, 15578, 15584, 15593,
                    15604, 15614, 15619, 15622, 15633, 15639, 15646, 15673, 15674, 15684, 15691, 15694, 15696, 15701, 15705, 15715,
                    15729, 15730, 15741, 15745, 15748, 15757, 15765, 15766, 15775, 15776, 15779, 15786, 15788, 15813, 15814, 15842,
                    15851, 15862, 15875, 15878, 15889, 15896, 15901, 15908, 15911, 15915, 15938, 15944, 15950, 15955, 15974, 15978,
                    15980, 15983, 15991, 16004, 16011, 16016, 16019, 16025, 16041, 16064, 16067, 16074, 16082, 16103, 16109, 16122,
                    16149, 16170, 16175, 16178, 16189, 16202, 16204, 16222, 16231, 16238, 16262, 16265, 16276, 16285, 16313, 16314,
                    16321, 16327, 16333, 16334, 16364, 16369, 16370, 16375, 16376, 16379, 16393, 16402, 16408, 16418, 16438, 16441,
                    16447, 16450, 16455, 16459, 16483, 16490, 16492, 16495, 16523, 16528, 16543, 16553, 16562, 16564, 16576, 16588,
                    16612, 16630, 16654, 16656, 16668, 16672, 16675, 16678, 16681, 16689, 16699, 16719, 16734, 16737, 16750, 16752,
                    16757, 16761, 16773, 16774, 16801, 16802, 16822, 16831, 16840, 16854, 16858, 16863, 16867, 16876, 16891, 16894,
                    16898, 16907, 16915, 16917, 16922, 16924, 16933, 16938, 16952, 16960, 16963, 16969, 16978, 17014, 17020, 17034,
                    17036, 17042, 17047, 17051, 17054, 17063, 17069, 17075, 17077, 17089, 17090, 17107, 17126, 17135, 17150, 17162,
                    17169, 17172, 17175, 17176, 17191, 17209, 17218, 17220, 17227, 17229, 17238, 17251, 17257, 17258, 17272, 17277,
                    17308, 17311, 17321, 17329, 17342, 17344, 17350, 17356, 17371, 17374, 17392, 17402, 17410, 17421, 17424, 17427,
                    17434, 17449, 17452, 17463, 17470, 17477, 17482, 17490, 17496, 17508, 17511, 17536, 17563, 17570, 17581, 17590,
                    17608, 17616, 17621, 17628, 17644, 17649, 17652, 17679, 17691, 17693, 17697, 17698, 17700, 17704, 17707, 17715,
                    17718, 17721, 17722, 17727, 17729, 17747, 17754, 17756, 17760, 17765, 17778, 17784, 17794, 17805, 17806, 17817,
                    17824, 17839, 17842, 17844, 17851, 17862, 17868, 17871, 17873, 17896, 17904, 17909, 17920, 17923, 17947, 17950,
                    17956, 17959, 17966, 17971, 17973, 17992, 18006, 18009, 18010, 18022, 18039, 18046, 18069, 18074, 18076, 18085,
                    18086, 18095, 18100, 18109, 18121, 18127, 18129, 18132, 18141, 18146, 18151, 18157, 18160, 18183, 18204, 18218,
                    18226, 18238, 18245, 18249, 18260, 18267, 18270, 18273, 18274, 18276, 18283, 18307, 18314, 18321, 18334, 18355,
                    18364, 18372, 18390, 18399, 18415, 18420, 18434, 18443, 18446, 18460, 18467, 18482, 18484, 18501, 18506, 18516,
                    18519, 18547, 18569, 18575, 18589, 18590, 18605, 18611, 18625, 18652, 18665, 18673, 18674, 18683, 18688, 18691,
                    18698, 18717, 18733, 18754, 18759, 18760, 18771, 18777, 18796, 18799, 18807, 18813, 18817, 18820, 18827, 18829,
                    18838, 18842, 18848, 18853, 18885, 18890, 18898, 18903, 18910, 18913, 18931, 18934, 18956, 18961, 18968, 18978,
                    18983, 18987, 18992, 18997, 19002, 19004, 19010, 19012, 19030, 19043, 19049, 19079, 19086, 19100, 19103, 19107,
                    19109, 19113, 19116, 19127, 19134, 19141, 19179, 19193, 19194, 19201, 19208, 19214, 19225, 19226, 19235, 19242,
                    19264, 19267, 19293, 19309, 19318, 19324, 19337, 19340, 19345, 19346, 19352, 19364, 19382, 19391, 19405, 19411,
                    19439, 19442, 19444, 19451, 19465, 19471, 19474, 19476, 19479, 19485, 19489, 19496, 19507, 19513, 19514, 19521,
                    19524, 19546, 19552, 19555, 19557, 19575, 19579, 19591, 19595, 19605, 19615, 19616, 19626, 19631, 19634, 19640,
                    19645, 19678, 19681, 19682, 19706, 19708, 19713, 19714, 19720, 19725, 19743, 19753, 19756, 19759, 19762, 19776,
                    19786, 19799, 19800, 19803, 19806, 19815, 19816, 19857, 19860, 19874, 19883, 19885, 19886, 19893, 19932, 19960,
                    19972, 19975, 20005, 20009, 20010, 20012, 20023, 20024, 20030, 20038, 20041, 20055, 20059, 20061, 20080, 20086,
                    20089, 20095, 20111, 20116, 20130, 20136, 20147, 20153, 20156, 20167, 20173, 20182, 20185, 20202, 20209, 20229,
                    20233, 20236, 20241, 20242, 20248, 20278, 20282, 20299, 20307, 20313, 20314, 20320, 20326, 20332, 20350, 20360,
                    20371, 20373, 20377, 20390, 20396, 20404, 20413, 20434, 20436, 20440, 20443, 20445, 20464, 20476, 20494, 20496,
                    20501, 20518, 20527, 20529, 20535, 20544, 20568, 20589, 20592, 20601, 20617, 20625, 20626, 20632, 20638, 20644,
                    20647, 20662, 20671, 20674, 20683, 20704, 20724, 20742, 20748, 20765, 20776, 20789, 20796, 20802, 20807, 20814,
                    20821, 20832, 20835, 20847, 20859, 20871, 20883, 20886, 20892, 20906, 20908, 20957, 20962, 20968, 20979, 20991,
                    20998, 21007, 21010, 21026, 21037, 21038, 21045, 21057, 21082, 21093, 21094, 21105, 21117, 21121, 21124, 21136,
                    21139, 21142, 21145, 21167, 21170, 21175, 21179, 21182, 21193, 21194, 21207, 21217, 21224, 21244, 21247, 21252,
                    21264, 21273, 21289, 21290, 21295, 21297, 21309, 21318, 21322, 21324, 21329, 21332, 21336, 21342, 21351, 21355,
                    21363, 21372, 21382, 21388, 21394, 21403, 21409, 21416, 21421, 21424, 21444, 21453, 21466, 21471, 21495, 21499,
                    21504, 21507, 21521, 21527, 21555, 21558, 21562, 21570, 21576, 21579, 21581, 21587, 21590, 21599, 21605, 21612,
                    21618, 21630, 21639, 21640, 21643, 21648, 21669, 21679, 21681, 21687, 21688, 21706, 21713, 21714, 21716, 21730,
                    21732, 21749, 21756, 21774, 21779, 21781, 21786, 21792, 21810, 21819, 21829, 21830, 21844, 21853, 21867, 21869,
                    21882, 21891, 21898, 21917, 21934, 21946, 21948, 21963, 21966, 21968, 21973, 21977, 21980, 21984, 21994, 21999,
                    22018, 22020, 22029, 22032, 22041, 22047, 22048, 22071, 22075, 22078, 22083, 22089, 22097, 22110, 22113, 22119,
                    22120, 22126, 22133, 22134, 22140, 22147, 22161, 22164, 22173, 22197, 22207, 22210, 22216, 22234, 22257, 22264,
                    22278, 22284, 22287, 22299, 22301, 22308, 22337, 22349, 22350, 22357, 22364, 22373, 22377, 22380, 22386, 22391,
                    22392, 22411, 22422, 22425, 22431, 22438, 22441, 22442, 22449, 22452, 22461, 22467, 22479, 22484, 22487, 22493,
                    22510, 22521, 22533, 22534, 22543, 22548, 22552, 22558, 22561, 22562, 22568, 22571, 22574, 22581, 22594, 22603,
                    22605, 22606, 22623, 22630, 22651, 22657, 22669, 22670, 22677, 22682, 22698, 22712, 22715, 22729, 22732, 22735,
                    22738, 22740, 22750, 22753, 22760, 22765, 22768, 22778, 22785, 22809, 22810, 22812, 22828, 22840, 22845, 22848,
                    22857, 22877, 22882, 22887, 22894, 22912, 22930, 22936, 22939, 22957, 22970, 22980, 22984, 22992, 22998, 23001,
                    23044, 23061, 23062, 23071, 23075, 23089, 23090, 23096, 23101, 23114, 23121, 23124, 23127, 23128, 23137, 23138,
                    23150, 23155, 23168, 23173, 23174, 23195, 23202, 23225, 23226, 23239, 23253, 23263, 23264, 23281, 23282, 23288,
                    23294, 23302, 23311, 23320, 23325, 23356, 23374, 23388, 23402, 23415, 23421, 23426, 23443, 23446, 23455, 23461,
                    23468, 23471, 23485, 23486, 23488, 23498, 23500, 23515, 23521, 23527, 23534, 23546, 23559, 23560, 23566, 23584,
                    23587, 23594, 23602, 23607, 23616, 23621, 23631, 23634, 23636, 23649, 23652, 23679, 23686, 23697, 23703, 23714,
                    23719, 23723, 23726, 23728, 23733, 23740, 23751, 23755, 23765, 23766, 23769, 23779, 23794, 23796, 23803, 23813,
                    23820, 23841, 23853, 23861, 23862, 23873, 23876, 23897, 23898, 23903, 23904, 23910, 23931, 23933, 23934, 23943,
                    23952, 23955, 23962, 23971, 23978, 23998, 24003, 24009, 24017, 24045, 24046, 24060, 24064, 24076, 24079, 24087,
                    24094, 24100, 24118, 24121, 24132, 24147, 24165, 24172, 24177, 24184, 24187, 24213, 24217, 24223, 24230, 24234,
                    24241, 24248, 24259, 24262, 24271, 24279, 24286, 24289, 24295, 24296, 24299, 24314, 24336, 24342, 24346, 24357,
                    24367, 24370, 24372, 24387, 24389, 24402, 24414, 24420, 24435, 24437, 24442, 24444, 24447, 24468, 24475, 24484,
                    24493, 24494, 24496, 24514, 24519, 24533, 24538, 24559, 24568, 24573, 24577, 24587, 24592, 24601, 24602, 24608,
                    24645, 24650, 24657, 24664, 24670, 24673, 24676, 24680, 24686, 24700, 24704, 24714, 24728, 24731, 24733, 24747,
                    24749, 24750, 24764, 24782, 24784, 24793, 24794, 24810, 24817, 24820, 24823, 24832, 24855, 24859, 24862, 24877,
                    24890, 24900, 24904, 24909, 24910, 24915, 24922, 24937, 24945, 24962, 24964, 24991, 24992, 24995, 25001, 25009,
                    25019, 25021, 25029, 25034, 25036, 25039, 25057, 25063, 25064, 25075, 25077, 25084, 25088, 25091, 25105, 25134,
                    25165, 25183, 25184, 25202, 25204, 25211, 25223, 25224, 25235, 25241, 25253, 25258, 25277, 25278, 25280, 25290,
                    25295, 25300, 25307, 25319, 25323, 25326, 25333, 25334, 25337, 25348, 25351, 25372, 25381, 25403, 25406, 25431,
                    25437, 25453, 25459, 25462, 25466, 25477, 25484, 25495, 25501, 25506, 25511, 25515, 25525, 25529, 25532, 25538,
                    25549, 25552, 25564, 25567, 25574, 25577, 25583, 25588, 25603, 25610, 25615, 25624, 25636, 25639, 25643, 25645,
                    25648, 25671, 25678, 25689, 25708, 25716, 25725, 25730, 25739, 25747, 25753, 25790, 25797, 25804, 25809, 25816,
                    25825, 25831, 25845, 25846, 25867, 25870, 25897, 25908, 25937, 25940, 25944, 25949, 25954, 25960, 25963, 25966,
                    25978, 25996, 26007, 26011, 26027, 26032, 26038, 26049, 26052, 26055, 26085, 26089, 26090, 26098, 26104, 26110,
                    26113, 26126, 26133, 26138, 26140, 26156, 26159, 26176, 26186, 26203, 26210, 26222, 26224, 26229, 26233, 26236,
                    26239, 26267, 26291, 26293, 26298, 26303, 26308, 26312, 26315, 26342, 26354, 26363, 26365, 26383, 26391, 26398,
                    26416, 26421, 26425, 26434, 26439, 26453, 26454, 26470, 26491, 26493, 26500, 26509, 26518, 26531, 26533, 26540,
                    26552, 26566, 26569, 26583, 26590, 26596, 26624, 26636, 26667, 26669, 26672, 26675, 26687, 26689, 26696, 26702,
                    26714, 26716, 26729, 26732, 26737, 26747, 26773, 26780, 26801, 26811, 26813, 26831, 26836, 26839, 26849, 26862,
                    26869, 26873, 26874, 26888, 26891, 26896, 26899, 26912, 26921, 26935, 26941, 26949, 26954, 26967, 26977, 27011,
                    27018, 27026, 27028, 27032, 27044, 27053, 27062, 27065, 27068, 27080, 27083, 27093, 27098, 27107, 27110, 27114,
                    27121, 27127, 27137, 27144, 27155, 27171, 27178, 27180, 27185, 27191, 27192, 27195, 27212, 27215, 27218, 27230,
                    27234, 27245, 27248, 27263, 27279, 27288, 27294, 27303, 27304, 27312, 27330, 27341, 27342, 27344, 27353, 27356,
                    27366, 27369, 27377, 27387, 27409, 27437, 27443, 27446, 27450, 27452, 27457, 27472, 27475, 27487, 27488, 27493,
                    27506, 27508, 27521, 27522, 27527, 27536, 27539, 27541, 27546, 27557, 27564, 27572, 27576, 27584, 27608, 27611,
                    27618, 27620, 27627, 27630, 27637, 27638, 27641, 27647, 27650, 27676, 27683, 27690, 27695, 27700, 27704, 27709,
                    27715, 27727, 27745, 27763, 27769, 27770, 27796, 27810, 27812, 27834, 27847, 27848, 27862, 27868, 27881, 27884,
                    27890, 27895, 27904, 27914, 27919, 27921, 27931, 27933, 27938, 27943, 27949, 28005, 28012, 28017, 28024, 28030,
                    28034, 28045, 28048, 28054, 28063, 28069, 28070, 28073, 28084, 28096, 28114, 28126, 28141, 28149, 28165, 28177,
                    28189, 28190, 28203, 28206, 28208, 28220, 28226, 28231, 28249, 28256, 28259, 28265, 28266, 28271, 28274, 28280,
                    28296, 28316, 28319, 28343, 28344, 28357, 28358, 28364, 28370, 28372, 28379, 28388, 28392, 28395, 28406, 28418,
                    28423, 28435, 28444, 28454, 28468, 28477, 28478, 28480, 28486, 28489, 28500, 28507, 28519, 28523, 28534, 28547,
                    28549, 28562, 28571, 28583, 28584, 28595, 28597, 28604, 28607, 28609, 28612, 28630, 28640, 28669, 28677, 28682,
                    28690, 28695, 28696, 28717, 28730, 28737, 28752, 28761, 28774, 28783, 28791, 28801, 28802, 28814, 28837, 28838,
                    28842, 28844, 28849, 28864, 28867, 28881, 28882, 28893, 28903, 28907, 28909, 28921, 28927, 28929, 28935, 28941,
                    28942, 28954, 28959, 28960, 28990, 28992, 29004, 29010, 29012, 29021, 29035, 29040, 29059, 29068, 29080, 29086,
                    29089, 29090, 29095, 29113, 29127, 29131, 29148, 29161, 29172, 29179, 29188, 29192, 29195, 29206, 29210, 29215,
                    29221, 29222, 29236, 29246, 29248, 29253, 29272, 29288, 29293, 29294, 29308, 29318, 29321, 29322, 29339, 29365,
                    29369, 29372, 29377, 29392, 29401, 29402, 29418, 29437, 29445, 29446, 29460, 29467, 29473, 29476, 29488, 29511,
                    29512, 29517, 29523, 29526, 29535, 29539, 29545, 29559, 29560, 29589, 29593, 29599, 29603, 29610, 29629, 29644,
                    29656, 29662, 29675, 29692, 29704, 29707, 29715, 29717, 29722, 29731, 29740, 29743, 29752, 29760, 29766, 29770,
                    29794, 29796, 29814, 29817, 29829, 29841, 29851, 29860, 29864, 29869, 29870, 29889, 29896, 29901, 29913, 29919,
                    29920, 29947, 29958, 29975, 29981, 29985, 29998, 30010, 30012, 30044, 30048, 30051, 30063, 30065, 30072, 30077,
                    30087, 30088, 30101, 30112, 30121, 30122, 30130, 30135, 30139, 30142, 30164, 30167, 30180, 30202, 30211, 30214,
                    30217, 30223, 30228, 30235, 30256, 30265, 30286, 30293, 30298, 30310, 30314, 30327, 30333, 30334, 30337, 30338,
                    30344, 30349, 30352, 30362, 30374, 30380, 30388, 30391, 30395, 30403, 30409, 30424, 30427, 30430, 30451, 30460,
                    30475, 30480, 30485, 30486, 30489, 30492, 30499, 30511, 30533, 30534, 30540, 30545, 30552, 30557, 30558, 30567,
                    30568, 30581, 30597, 30607, 30621, 30632, 30650, 30655, 30657, 30660, 30669, 30675, 30678, 30682, 30687, 30688,
                    30698, 30711, 30728, 30733, 30741, 30746, 30752, 30764, 30769, 30784, 30796, 30799, 30804, 30811, 30814, 30818,
                    30827, 30838, 30842, 30854, 30863, 30865, 30877, 30881, 30887, 30908, 30916, 30919, 30925, 30928, 30944, 30947,
                    30950, 30968, 30971, 30973, 30976, 30986, 30993, 30994, 31012, 31016, 31029, 31039, 31041, 31042, 31044, 31053,
                    31059, 31062, 31077, 31095, 31101, 31105, 31118, 31129, 31132, 31141, 31159, 31183, 31185, 31195, 31202, 31213,
                    31238, 31241, 31252, 31262, 31265, 31295, 31300, 31303, 31317, 31318, 31324, 31338, 31340, 31345, 31355, 31362,
                    31371, 31373, 31381, 31391, 31409, 31410, 31412, 31434, 31458, 31467, 31470, 31475, 31481, 31482, 31484, 31492,
                    31507, 31514, 31538, 31547, 31549, 31555, 31557, 31597, 31598, 31610, 31625, 31634, 31643, 31645, 31659, 31669,
                    31670, 31682, 31694, 31717, 31718, 31729, 31736, 31742, 31749, 31773, 31777, 31778, 31784, 31801, 31812, 31821,
                    31822, 31836, 31850, 31855, 31858, 31860, 31886, 31891, 31909, 31928, 31931, 31934, 31941, 31942, 31945, 31951,
                    31959, 31963, 31970, 31984, 31987, 31990, 32001, 32007, 32008, 32025, 32035, 32038, 32047, 32049, 32062, 32067,
                    32070, 32073, 32074, 32079, 32081, 32082, 32107, 32127, 32145, 32151, 32152, 32173, 32174, 32186, 32194, 32196,
                    32200, 32208, 32218, 32236, 32260, 32263, 32288, 32298, 32315, 32332, 32340, 32354, 32359, 32366, 32368, 32377,
                    32384, 32399, 32417, 32424, 32427, 32429, 32438, 32442, 32447, 32469, 32479, 32483, 32498, 32503, 32507, 32521,
                    32532, 32539, 32551, 32572, 32577, 32578, 32587, 32592, 32602, 32604, 32613, 32625, 32631, 32641, 32642, 32644,
                    32656, 32678, 32681, 32701, 32713, 32721, 32727, 32731, 32734, 32740, 32773, 32774, 32785, 32788, 32792, 32797,
                    32801, 32811, 32821, 32828, 32839, 32854, 32867, 32870, 32873, 32881, 32891, 32900, 32903, 32910, 32917, 32922,
                    32928, 32945, 32946, 32951, 32958, 32965, 32978, 32980, 32983, 32987, 33023, 33031, 33032, 33035, 33038, 33040,
                    33043, 33050, 33059, 33080, 33098, 33108, 33115, 33117, 33133, 33134, 33157, 33169, 33170, 33176, 33182, 33192,
                    33205, 33212, 33215, 33217, 33227, 33229, 33238, 33241, 33260, 33271, 33278, 33293, 33294, 33296, 33302, 33305,
                    33329, 33330, 33332, 33354, 33383, 33387, 33397, 33408, 33417, 33420, 33423, 33431, 33438, 33442, 33444, 33448,
                    33456, 33459, 33466, 33473, 33476, 33491, 33494, 33507, 33514, 33521, 33528, 33534, 33536, 33551, 33554, 33563,
                    33575, 33579, 33582, 33601, 33602, 33614, 33625, 33628, 33637, 33656, 33665, 33683, 33695, 33696, 33702, 33708,
                    33711, 33716, 33728, 33737, 33761, 33774, 33782, 33788, 33791, 33793, 33811, 33813, 33818, 33829, 33842, 33854,
                    33856, 33868, 33879, 33885, 33886, 33892, 33907, 33913, 33923, 33925, 33935, 33937, 33954, 33963, 33966, 33977,
                    33978, 33991, 33998, 34012, 34016, 34025, 34033, 34036, 34045, 34065, 34078, 34093, 34101, 34108, 34111, 34120,
                    34123, 34125, 34153, 34171, 34174, 34178, 34183, 34190, 34201, 34211, 34220, 34225, 34237, 34249, 34250, 34264,
                    34269, 34276, 34279, 34293, 34303, 34310, 34340, 34349, 34352, 34355, 34358, 34362, 34376, 34381, 34387, 34389,
                    34394, 34410, 34423, 34434, 34436, 34448, 34453, 34454, 34460, 34464, 34479, 34491, 34501, 34508, 34516, 34529,
                    34530, 34553, 34564, 34568, 34571, 34582, 34586, 34598, 34604, 34610, 34619, 34621, 34633, 34634, 34657, 34682,
                    34688, 34691, 34694, 34706, 34708, 34724, 34727, 34751, 34753, 34759, 34763, 34777, 34778, 34780, 34796, 34799,
                    34801, 34817, 34824, 34827, 34837, 34841, 34853, 34858, 34877, 34886, 34895, 34897, 34898, 34916, 34923, 34928,
                    34934, 34940, 34944, 34947, 34953, 34956, 34967, 34968, 34971, 34974, 34977, 34980, 34983, 34998, 35004, 35010,
                    35012, 35030, 35039, 35058, 35063, 35082, 35095, 35096, 35101, 35102, 35105, 35112, 35118, 35120, 35130, 35143,
                    35147, 35152, 35157, 35164, 35178, 35195, 35197, 35201, 35207, 35231, 35249, 35250, 35256, 35259, 35262, 35273,
                    35282, 35298, 35307, 35328, 35334, 35343, 35345, 35355, 35388, 35399, 35403, 35408, 35413, 35418, 35434, 35436,
                    35448, 35460, 35475, 35494, 35497, 35503, 35508, 35511, 35518, 35520, 35530, 35537, 35543, 35560, 35566, 35571,
                    35598, 35609, 35612, 35615, 35616, 35622, 35626, 35633, 35636, 35645, 35653, 35663, 35665, 35682, 35687, 35688,
                    35691, 35696, 35727, 35741, 35742, 35746, 35748, 35752, 35770, 35811, 35817, 35832, 35838, 35850, 35863, 35870,
                    35879, 35880, 35891, 35893, 35903, 35905, 35926, 35930, 35941, 35956, 35963, 35970, 35984, 35996, 36005, 36012,
                    36015, 36030, 36035, 36042, 36047, 36049, 36059, 36071, 36080, 36085, 36097, 36110, 36115, 36118, 36124, 36137,
                    36138, 36155, 36160, 36189, 36190, 36203, 36211, 36218, 36230, 36241, 36244, 36253, 36257, 36264, 36272, 36290,
                    36292, 36301, 36309, 36313, 36316, 36319, 36330, 36335, 36347, 36353, 36356, 36368, 36374, 36377, 36384, 36407,
                    36413, 36419, 36425, 36426, 36446, 36461, 36467, 36474, 36480, 36486, 36489, 36495, 36525, 36526, 36533, 36534,
                    36540, 36555, 36563, 36565, 36588, 36593, 36596, 36606, 36614, 36626, 36628, 36641, 36642, 36651, 36653, 36673,
                    36676, 36679, 36694, 36698, 36704, 36714, 36721, 36722, 36727, 36749, 36758, 36761, 36771, 36788, 36797, 36805,
                    36830, 36833, 36839, 36843, 36860, 36866, 36871, 36875, 36877, 36880, 36892, 36911, 36914, 36916, 36923, 36940,
                    36943, 36948, 36957, 36958, 36967, 36974, 36981, 37001, 37012, 37015, 37019, 37035, 37040, 37063, 37064, 37069,
                    37077, 37093, 37097, 37106, 37115, 37118, 37123, 37129, 37130, 37135, 37137, 37138, 37140, 37144, 37149, 37156,
                    37159, 37163, 37165, 37183, 37185, 37195, 37198, 37203, 37212, 37233, 37236, 37240, 37256, 37264, 37267, 37273,
                    37295, 37315, 37330, 37336, 37339, 37342, 37357, 37365, 37372, 37381, 37399, 37416, 37429, 37436, 37442, 37451,
                    37453, 37475, 37482, 37489, 37490, 37523, 37525, 37530, 37535, 37539, 37548, 37559, 37560, 37566, 37597, 37601,
                    37602, 37604, 37616, 37622, 37658, 37660, 37667, 37684, 37693, 37694, 37696, 37706, 37711, 37730, 37735, 37742,
                    37744, 37759, 37777, 37783, 37784, 37793, 37800, 37818, 37823, 37825, 37828, 37832, 37849, 37856, 37861, 37862,
                    37886, 37893, 37915, 37922, 37936, 37939, 37945, 37956, 37960, 37971, 37980, 38002, 38007, 38018, 38020, 38038,
                    38041, 38044, 38048, 38051, 38054, 38065, 38066, 38077, 38090, 38109, 38119, 38120, 38123, 38146, 38155, 38160,
                    38181, 38188, 38199, 38203, 38225, 38226, 38231, 38244, 38248, 38259, 38268, 38271, 38272, 38278, 38292, 38296,
                    38318, 38326, 38338, 38347, 38373, 38378, 38380, 38388, 38392, 38397, 38398, 38401, 38404, 38422, 38432, 38469,
                    38482, 38493, 38507, 38518, 38521, 38527, 38533, 38534, 38552, 38561, 38576, 38581, 38585, 38594, 38606, 38613,
                    38617, 38634, 38644, 38651, 38661, 38665, 38668, 38674, 38689, 38695, 38696, 38707, 38710, 38716, 38733, 38736,
                    38742, 38751, 38752, 38775, 38779, 38792, 38798, 38819, 38822, 38839, 38851, 38854, 38863, 38865, 38887, 38896,
                    38901, 38912, 38918, 38929, 38941, 38948, 38965, 38969, 38977, 38987, 38992, 38995, 39001, 39004, 39011, 39018,
                    39025, 39031, 39032, 39044, 39056, 39065, 39066, 39084, 39095, 39099, 39101, 39102, 39104, 39109, 39114, 39121,
                    39122, 39127, 39150, 39172, 39181, 39184, 39193, 39199, 39200, 39206, 39209, 39235, 39238, 39242, 39244, 39280,
                    39290, 39292, 39296, 39305, 39308, 39311, 39326, 39329, 39330, 39336, 39339, 39353, 39359, 39374, 39391, 39392,
                    39402, 39409, 39410, 39415, 39419, 39431, 39432, 39438, 39443, 39445, 39449, 39461, 39462, 39485, 39508, 39515,
                    39522, 39533, 39541, 39542, 39557, 39570, 39575, 39576, 39591, 39597, 39603, 39610, 39612, 39617, 39620, 39624,
                    39627, 39635, 39641, 39666, 39668, 39686, 39692, 39700, 39709, 39726, 39728, 39737, 39738, 39755, 39757, 39758,
                    39765, 39766, 39769, 39779, 39785, 39794, 39796, 39809, 39815, 39829, 39834, 39839, 39850, 39857, 39881, 39905,
                    39912, 39938, 39940, 39944, 39955, 39961, 39977, 39980, 39986, 39988, 39991, 39998, 40003, 40005, 40023, 40024,
                    40045, 40046, 40058, 40088, 40091, 40098, 40109, 40112, 40124, 40130, 40153, 40166, 40175, 40183, 40184, 40207,
                    40215, 40225, 40235, 40240, 40255, 40263, 40270, 40277, 40282, 40297, 40306, 40315, 40333, 40334, 40336, 40342,
                    40345, 40348, 40355, 40372, 40381, 40390, 40407, 40435, 40437, 40441, 40444, 40458, 40460, 40481, 40484, 40487,
                    40493, 40494, 40501, 40502, 40525, 40550, 40553, 40559, 40562, 40573, 40574, 40578, 40583, 40584, 40587, 40597,
                    40620, 40623, 40631, 40643, 40660, 40667, 40676, 40693, 40697, 40708, 40718, 40726, 40735, 40739, 40748, 40760,
                    40763, 40773, 40774, 40786, 40791, 40798, 40801, 40808, 40811, 40813, 40825, 40831, 40835, 40856, 40861, 40880,
                    40889, 40912, 40918, 40924, 40927, 40928, 40938, 40952, 40957, 40961, 40968, 40974, 40986, 40992, 41001, 41004,
                    41022, 41033, 41036, 41039, 41044, 41064, 41078, 41091, 41103, 41108, 41112, 41127, 41128, 41136, 41141, 41146,
                    41156, 41159, 41163, 41165, 41166, 41184, 41193, 41202, 41211, 41216, 41239, 41243, 41249, 41262, 41267, 41276,
                    41279, 41305, 41306, 41312, 41317, 41321, 41332, 41335, 41345, 41346, 41351, 41365, 41388, 41399, 41405, 41414,
                    41432, 41454, 41461, 41462, 41471, 41478, 41490, 41506, 41508, 41512, 41517, 41520, 41529, 41530, 41544, 41550,
                    41571, 41577, 41580, 41595, 41613, 41622, 41635, 41642, 41661, 41676, 41681, 41684, 41687, 41688, 41694, 41698,
                    41710, 41712, 41721, 41722, 41729, 41744, 41747, 41753, 41766, 41783, 41790, 41810, 41819, 41825, 41828, 41843,
                    41846, 41862, 41871, 41874, 41876, 41892, 41899, 41916, 41928, 41957, 41964, 41969, 41981, 41996, 41999, 42001,
                    42017, 42023, 42027, 42032, 42035, 42044, 42050, 42061, 42062, 42073, 42098, 42104, 42109, 42120, 42125, 42131,
                    42143, 42153, 42176, 42182, 42188, 42203, 42216, 42219, 42227, 42234, 42251, 42254, 42256, 42259, 42282, 42289,
                    42302, 42307, 42310, 42322, 42328, 42338, 42349, 42367, 42386, 42398, 42404, 42413, 42419, 42421, 42422, 42431,
                    42445, 42448, 42451, 42454, 42458, 42470, 42476, 42500, 42509, 42538, 42543, 42545, 42546, 42563, 42570, 42580,
                    42584, 42589, 42608, 42618, 42629, 42636, 42639, 42644, 42647, 42651, 42654, 42658, 42669, 42678, 42689, 42695,
                    42696, 42710, 42716, 42730, 42732, 42747, 42750, 42752, 42761, 42772, 42776, 42779, 42797, 42815, 42824, 42837,
                    42844, 42868, 42877, 42888, 42891, 42912, 42918, 42921, 42927, 42949, 42953, 42967, 42974, 42989, 42995, 42997,
                    43007, 43018, 43025, 43031, 43032, 43038, 43041, 43054, 43074, 43085, 43097, 43098, 43110, 43119, 43131, 43143,
                    43144, 43152, 43157, 43178, 43180, 43192, 43197, 43203, 43220, 43236, 43245, 43246, 43260, 43265, 43271, 43285,
                    43286, 43290, 43299, 43314, 43323, 43337, 43348, 43355, 43357, 43358, 43361, 43362, 43364, 43368, 43376, 43385,
                    43386, 43391, 43397, 43431, 43438, 43440, 43452, 43463, 43470, 43478, 43481, 43488, 43491, 43506, 43512, 43539,
                    43567, 43579, 43584, 43601, 43614, 43617, 43623, 43630, 43647, 43658, 43663, 43665, 43691, 43701, 43705, 43708,
                    43719, 43731, 43737, 43740, 43747, 43749, 43750, 43754, 43764, 43767, 43768, 43773, 43785, 43788, 43810, 43819,
                    43834, 43853, 43866, 43871, 43872, 43881, 43889, 43902, 43926, 43935, 43946, 43951, 43953, 43971, 44008, 44011,
                    44026, 44033, 44048, 44057, 44058, 44067, 44081, 44087, 44099, 44105, 44106, 44114, 44116, 44130, 44139, 44141,
                    44153, 44159, 44160, 44170, 44172, 44187, 44190, 44193, 44199, 44213, 44218, 44223, 44235, 44243, 44262, 44283,
                    44291, 44293, 44308, 44327, 44341, 44346, 44348, 44354, 44366, 44368, 44373, 44384, 44390, 44393, 44394, 44399,
                    44401, 44408, 44411, 44417, 44420, 44424, 44444, 44451, 44453, 44466, 44472, 44475, 44486, 44500, 44510, 44531,
                    44534, 44553, 44559, 44564, 44580, 44583, 44589, 44592, 44609, 44627, 44633, 44643, 44646, 44650, 44674, 44676,
                    44680, 44683, 44703, 44704, 44716, 44733, 44748, 44759, 44763, 44781, 44782, 44789, 44794, 44799, 44804, 44813,
                    44822, 44838, 44842, 44849, 44856, 44870, 44887, 44904, 44915, 44917, 44921, 44928, 44933, 44934, 44937, 44940,
                    44951, 44961, 44962, 44971, 44982, 44985, 44996, 44999, 45014, 45029, 45033, 45036, 45047, 45065, 45076, 45085,
                    45086, 45090, 45107, 45119, 45121, 45128, 45139, 45151, 45157, 45162, 45164, 45176, 45179, 45198, 45209, 45231,
                    45234, 45236, 45251, 45260, 45268, 45277, 45299, 45301, 45338, 45344, 45349, 45364, 45367, 45371, 45373, 45376,
                    45381, 45400, 45406, 45416, 45422, 45427, 45440, 45458, 45467, 45474, 45476, 45488, 45494, 45497, 45500, 45512,
                    45515, 45523, 45542, 45553, 45559, 45589, 45594, 45596, 45603, 45605, 45610, 45630, 45638, 45641, 45647, 45655,
                    45659, 45665, 45689, 45695, 45702, 45708, 45726, 45729, 45739, 45744, 45747, 45762, 45764, 45773, 45774, 45781,
                    45785, 45792, 45801, 45802, 45812, 45816, 45829, 45833, 45847, 45851, 45854, 45863, 45870, 45901, 45910, 45920,
                    45932, 45938, 45940, 45943, 45949, 45953, 45963, 45971, 45989, 45999, 46001, 46008, 46022, 46026, 46034, 46040,
                    46043, 46045, 46061, 46062, 46076, 46084, 46094, 46102, 46118, 46149, 46154, 46167, 46173, 46177, 46192, 46197,
                    46201, 46214, 46217, 46223, 46226, 46228, 46237, 46241, 46251, 46259, 46262, 46266, 46294, 46300, 46303, 46309,
                    46321, 46331, 46339, 46342, 46354, 46370, 46372, 46379, 46382, 46393, 46402, 46407, 46416, 46425, 46437, 46444,
                    46447, 46449, 46456, 46462, 46472, 46475, 46486, 46490, 46495, 46501, 46514, 46526, 46528, 46538, 46551, 46552,
                    46564, 46579, 46586, 46602, 46615, 46616, 46621, 46635, 46643, 46645, 46649, 46667, 46670, 46672, 46678, 46681,
                    46684, 46687, 46705, 46708, 46721, 46724, 46739, 46757, 46762, 46764, 46776, 46784, 46790, 46796, 46799, 46802,
                    46813, 46814, 46824, 46832, 46841, 46856, 46862, 46864, 46869, 46874, 46895, 46900, 46903, 46927, 46946, 46948,
                    46955, 46965, 46972, 46976, 46985, 46999, 47003, 47006, 47016, 47030, 47034, 47053, 47056, 47059, 47072, 47077,
                    47099, 47105, 47108, 47112, 47115, 47117, 47120, 47129, 47135, 47151, 47154, 47156, 47166, 47171, 47195, 47204,
                    47213, 47222, 47228, 47241, 47242, 47247, 47249, 47250, 47261, 47266, 47268, 47278, 47286, 47312, 47315, 47321,
                    47322, 47324, 47331, 47338, 47366, 47372, 47375, 47378, 47387, 47389, 47390, 47396, 47400, 47405, 47408, 47411,
                    47431, 47443, 47459, 47466, 47479, 47480, 47495, 47504, 47519, 47520, 47530, 47537, 47555, 47558, 47567, 47569,
                    47572, 47582, 47591, 47595, 47598, 47609, 47612, 47616, 47625, 47640, 47649, 47655, 47661, 47688, 47711, 47717,
                    47721, 47724, 47727, 47730, 47739, 47745, 47752, 47758, 47765, 47770, 47782, 47786, 47791, 47793, 47796, 47814,
                    47823, 47826, 47837, 47854, 47856, 47871, 47879, 47880, 47885, 47886, 47903, 47907, 47913, 47916, 47928, 47936,
                    47945, 47948, 47956, 47965, 47970, 47972, 47981, 47987, 48010, 48012, 48023, 48033, 48051, 48071, 48075, 48077,
                    48080, 48099, 48106, 48114, 48120, 48128, 48145, 48152, 48155, 48182, 48188, 48203, 48217, 48218, 48236, 48241,
                    48244, 48264, 48269, 48278, 48282, 48288, 48311, 48315, 48317, 48329, 48330, 48337, 48350, 48353, 48360, 48374,
                    48380, 48405, 48406, 48409, 48412, 48416, 48419, 48425, 48434, 48443, 48445, 48453, 48454, 48482, 48491, 48501,
                    48517, 48518, 48545, 48548, 48552, 48569, 48575, 48580, 48584, 48590, 48595, 48602, 48604, 48618, 48625, 48626,
                    48638, 48668, 48677, 48678, 48687, 48692, 48696, 48704, 48707, 48714, 48731, 48740, 48750, 48761, 48785, 48786,
                    48798, 48807, 48808, 48814, 48826, 48828, 48834, 48840, 48845, 48858, 48864, 48869, 48870, 48879, 48887, 48899,
                    48911, 48920, 48926, 48929, 48971, 48974, 48981, 48998, 49007, 49028, 49031, 49056, 49065, 49079, 49083, 49088,
                    49093, 49100, 49103, 49124, 49136, 49139, 49142, 49145, 49146, 49171, 49187, 49190, 49194, 49196, 49207, 49216,
                    49219, 49222, 49240, 49250, 49262, 49276, 49289, 49292, 49298, 49319, 49320, 49325, 49333, 49340, 49343, 49352,
                    49363, 49375, 49391, 49405, 49413, 49420, 49425, 49431, 49432, 49441, 49456, 49476, 49479, 49504, 49514, 49528,
                    49543, 49544, 49550, 49558, 49568, 49578, 49600, 49612, 49633, 49634, 49636, 49643, 49654, 49657, 49663, 49667,
                    49693, 49700, 49703, 49712, 49717, 49718, 49721, 49727, 49730, 49735, 49739, 49747, 49765, 49770, 49772, 49777,
                    49789, 49817, 49829, 49839, 49844, 49851, 49854, 49874, 49883, 49886, 49892, 49895, 49914, 49931, 49933, 49934,
                    49941, 49952, 49967, 49969, 49972, 49976, 49979, 49999, 50008, 50030, 50041, 50042, 50044, 50057, 50060, 50075,
                    50077, 50091, 50096, 50099, 50106, 50116, 50133, 50144, 50153, 50154, 50168, 50179, 50188, 50194, 50203, 50212,
                    50216, 50222, 50229, 50230, 50234, 50251, 50253, 50262, 50266, 50272, 50287, 50295, 50305, 50306, 50311, 50320,
                    50323, 50325, 50339, 50356, 50365, 50366, 50368, 50377, 50386, 50397, 50411, 50414, 50425, 50451, 50463, 50467,
                    50473, 50487, 50501, 50505, 50514, 50516, 50536, 50547, 50566, 50572, 50583, 50593, 50599, 50613, 50617, 50618,
                    50620, 50628, 50638, 50652, 50655, 50662, 50665, 50673, 50683, 50689, 50690, 50692, 50696, 50704, 50713, 50719,
                    50738, 50757, 50761, 50764, 50770, 50776, 50803, 50805, 50806, 50816, 50826, 50840, 50845, 50852, 50855, 50862,
                    50876, 50879, 50882, 50894, 50899, 50905, 50924, 50936, 50944, 50973, 50974, 51002, 51007, 51009, 51029, 51036,
                    51063, 51067, 51070, 51074, 51079, 51083, 51093, 51094, 51124, 51131, 51151, 51153, 51156, 51165, 51175, 51184,
                    51187, 51203, 51220, 51224, 51233, 51234, 51239, 51246, 51248, 51251, 51257, 51266, 51280, 51285, 51286, 51289,
                    51295, 51296, 51311, 51329, 51350, 51356, 51363, 51365, 51370, 51372, 51377, 51383, 51384, 51397, 51407, 51422,
                    51435, 51440, 51446, 51463, 51467, 51491, 51493, 51494, 51508, 51518, 51520, 51526, 51529, 51530, 51537, 51547,
                    51559, 51565, 51568, 51580, 51583, 51584, 51593, 51594, 51607, 51608, 51620, 51624, 51642, 51649, 51652, 51659,
                    51661, 51670, 51674, 51698, 51707, 51725, 51737, 51753, 51754, 51779, 51791, 51796, 51809, 51816, 51819, 51827,
                    51829, 51834, 51836, 51846, 51849, 51855, 51858, 51869, 51870, 51874, 51918, 51926, 51932, 51936, 51948, 51954,
                    51959, 51971, 51983, 51992, 51997, 52016, 52021, 52031, 52033, 52034, 52048, 52053, 52057, 52067, 52076, 52079,
                    52081, 52109, 52127, 52128, 52133, 52148, 52155, 52157, 52172, 52178, 52184, 52199, 52200, 52205, 52206, 52232,
                    52237, 52243, 52246, 52252, 52261, 52265, 52266, 52273, 52274, 52288, 52300, 52303, 52321, 52322, 52328, 52333,
                    52351, 52357, 52372, 52381, 52386, 52395, 52403, 52423, 52427, 52432, 52438, 52444, 52457, 52468, 52480, 52498,
                    52516, 52520, 52525, 52531, 52540, 52545, 52546, 52552, 52557, 52569, 52575, 52582, 52594, 52615, 52619, 52624,
                    52645, 52663, 52664, 52675, 52687, 52696, 52701, 52706, 52708, 52711, 52726, 52748, 52754, 52756, 52759, 52769,
                    52784, 52790, 52804, 52807, 52808, 52819, 52826, 52828, 52832, 52835, 52849, 52859, 52877, 52880, 52896, 52899,
                    52905, 52913, 52933, 52934, 52938, 52957, 52979, 52981, 52986, 53005, 53008, 53024, 53029, 53054, 53073, 53083,
                    53086, 53095, 53102, 53107, 53126, 53130, 53138, 53144, 53156, 53163, 53165, 53173, 53177, 53183, 53192, 53195,
                    53200, 53212, 53219, 53221, 53245, 53254, 53257, 53265, 53266, 53268, 53275, 53277, 53284, 53294, 53308, 53314,
                    53319, 53326, 53328, 53340, 53364, 53367, 53377, 53395, 53398, 53407, 53413, 53414, 53417, 53443, 53473, 53476,
                    53480, 53486, 53506, 53542, 53546, 53554, 53563, 53580, 53591, 53598, 53611, 53621, 53625, 53628, 53632, 53650,
                    53655, 53675, 53677, 53680, 53683, 53700, 53709, 53724, 53731, 53733, 53738, 53762, 53767, 53785, 53786, 53795,
                    53802, 53812, 53821, 53827, 53833, 53841, 53848, 53869, 53897, 53905, 53912, 53921, 53928, 53948, 53954, 53968,
                    53977, 53983, 53984, 54002, 54007, 54013, 54014, 54021, 54026, 54028, 54031, 54036, 54067, 54069, 54074, 54076,
                    54105, 54111, 54118, 54129, 54145, 54157, 54158, 54163, 54166, 54172, 54185, 54186, 54188, 54208, 54223, 54237,
                    54241, 54244, 54259, 54273, 54276, 54280, 54294, 54298, 54303, 54304, 54309, 54310, 54316, 54336, 54354, 54359,
                    54375, 54376, 54381, 54390, 54396, 54399, 54409, 54424, 54434, 54436, 54439, 54445, 54448, 54457, 54465, 54471,
                    54472, 54485, 54508, 54513, 54528, 54534, 54537, 54540, 54551, 54561, 54573, 54579, 54586, 54591, 54596, 54599,
                    54605, 54613, 54623, 54654, 54667, 54681, 54684, 54687, 54694, 54706, 54712, 54715, 54723, 54725, 54726, 54740,
                    54750, 54760, 54768, 54771, 54773, 54796, 54801, 54808, 54814, 54837, 54847, 54849, 54864, 54867, 54889, 54895,
                    54907, 54919, 54923, 54926, 54940, 54950, 54954, 54961, 54973, 54974, 54976, 54986, 54993, 55000, 55010, 55019,
                    55021, 55030, 55033, 55039, 55047, 55048, 55054, 55059, 55068, 55071, 55075, 55077, 55081, 55095, 55099, 55110,
                    55116, 55122, 55134, 55144, 55147, 55158, 55162, 55171, 55185, 55192, 55197, 55207, 55228, 55246, 55253, 55254,
                    55281, 55300, 55303, 55307, 55312, 55328, 55331, 55337, 55348, 55352, 55357, 55372, 55375, 55377, 55394, 55406,
                    55424, 55429, 55430, 55436, 55439, 55447, 55453, 55458, 55460, 55478, 55489, 55501, 55502, 55519, 55525, 55530,
                    55544, 55550, 55558, 55561, 55569, 55586, 55591, 55598, 55605, 55609, 55618, 55630, 55642, 55644, 55653, 55654,
                    55660, 55671, 55693, 55706, 55712, 55718, 55739, 55747, 55754, 55771, 55774, 55780, 55783, 55784, 55789, 55798,
                    55802, 55823, 55828, 55837, 55838, 55847, 55848, 55885, 55897, 55904, 55910, 55919, 55922, 55931, 55933, 55937,
                    55943, 55955, 55957, 55968, 55980, 55986, 55992, 56005, 56034, 56051, 56054, 56065, 56080, 56101, 56113, 56126,
                    56131, 56134, 56143, 56148, 56152, 56174, 56181, 56182, 56185, 56192, 56201, 56215, 56219, 56222, 56258, 56264,
                    56269, 56272, 56278, 56282, 56306, 56308, 56318, 56329, 56356, 56360, 56383, 56388, 56391, 56405, 56406, 56416,
                    56426, 56439, 56446, 56450, 56459, 56461, 56464, 56469, 56489, 56490, 56497, 56509, 56515, 56518, 56524, 56535,
                    56542, 56545, 56551, 56555, 56560, 56569, 56580, 56584, 56598, 56601, 56602, 56623, 56637, 56638, 56643, 56650,
                    56673, 56676, 56686, 56704, 56710, 56721, 56731, 56738, 56747, 56750, 56752, 56757, 56787, 56796, 56803, 56812,
                    56815, 56824, 56829, 56833, 56839, 56840, 56845, 56853, 56854, 56858, 56863, 56864, 56881, 56882, 56896, 56901,
                    56902, 56908, 56923, 56925, 56926, 56932, 56939, 56947, 56954, 56970, 56972, 56977, 56980, 56993, 57013, 57017,
                    57018, 57031, 57059, 57062, 57065, 57071, 57083, 57093, 57097, 57106, 57118, 57133, 57134, 57141, 57146, 57156,
                    57174, 57183, 57189, 57190, 57193, 57196, 57207, 57211, 57214, 57218, 57230, 57235, 57241, 57251, 57260, 57263,
                    57265, 57271, 57272, 57290, 57295, 57298, 57325, 57328, 57340, 57344, 57353, 57368, 57371, 57383, 57402, 57407,
                    57419, 57424, 57433, 57452, 57458, 57467, 57470, 57473, 57479, 57480, 57486, 57491, 57493, 57524, 57528, 57534,
                    57542, 57560, 57565, 57570, 57579, 57590, 57601, 57604, 57607, 57621, 57635, 57642, 57650, 57655, 57670, 57676,
                    57682, 57687, 57691, 57700, 57704, 57707, 57721, 57731, 57733, 57738, 57745, 57748, 57751, 57757, 57761, 57774,
                    57779, 57786, 57808, 57814, 57817, 57824, 57827, 57848, 57853, 57857, 57858, 57867, 57872, 57887, 57894, 57915,
                    57920, 57923, 57932, 57937, 57943, 57950, 57960, 57971, 57974, 57983, 57987, 57994, 58001, 58002, 58013, 58023,
                    58027, 58029, 58032, 58041, 58047, 58062, 58086, 58092, 58095, 58097, 58100, 58117, 58121, 58132, 58142, 58145,
                    58152, 58155, 58163, 58166, 58170, 58175, 58184, 58197, 58202, 58204, 58208, 58213, 58220, 58226, 58231, 58235,
                    58254, 58261, 58271, 58278, 58287, 58327, 58333, 58337, 58357, 58362, 58364, 58375, 58384, 58387, 58405, 58406,
                    58417, 58424, 58435, 58450, 58452, 58459, 58461, 58466, 58468, 58485, 58489, 58492, 58495, 58505, 58523, 58535,
                    58536, 58549, 58550, 58561, 58562, 58573, 58576, 58592, 58601, 58622, 58627, 58629, 58634, 58639, 58641, 58644,
                    58654, 58663, 58667, 58677, 58682, 58690, 58692, 58695, 58710, 58713, 58723, 58740, 58749, 58760, 58766, 58773,
                    58780, 58787, 58796, 58816, 58822, 58831, 58839, 58849, 58859, 58870, 58879, 58889, 58904, 58914, 58923, 58940,
                    58945, 58952, 58960, 58965, 58986, 59000, 59006, 59027, 59034, 59052, 59058, 59081, 59082, 59089, 59096, 59118,
                    59123, 59126, 59132, 59135, 59150, 59174, 59177, 59185, 59195, 59203, 59206, 59234, 59248, 59258, 59260, 59270,
                    59284, 59291, 59293, 59297, 59312, 59315, 59327, 59332, 59347, 59354, 59363, 59369, 59377, 59406, 59413, 59423,
                    59434, 59441, 59442, 59456, 59465, 59479, 59501, 59504, 59507, 59514, 59523, 59525, 59526, 59537, 59543, 59560,
                    59563, 59566, 59577, 59580, 59586, 59597, 59612, 59615, 59622, 59631, 59657, 59660, 59665, 59671, 59675, 59688,
                    59706, 59708, 59713, 59714, 59720, 59737, 59743, 59744, 59749, 59756, 59783, 59784, 59787, 59795, 59804, 59811,
                    59814, 59823, 59838, 59840, 59846, 59849, 59855, 59860, 59867, 59869, 59885, 59888, 59893, 59904, 59919, 59922,
                    59927, 59937, 59940, 59944, 59947, 59949, 59961, 59975, 59981, 59994, 59999, 60018, 60023, 60024, 60039, 60043,
                    60048, 60054, 60073, 60079, 60082, 60102, 60108, 60111, 60120, 60149, 60153, 60161, 60168, 60176, 60182, 60185,
                    60204, 60212, 60215, 60219, 60222, 60229, 60234, 60241, 60248, 60277, 60282, 60287, 60303, 60306, 60327, 60333,
                    60334, 60336, 60345, 60359, 60368, 60380, 60389, 60390, 60394, 60407, 60414, 60419, 60426, 60428, 60431, 60440,
                    60450, 60469, 60473, 60474, 60484, 60496, 60502, 60508, 60511, 60522, 60529, 60530, 60536, 60551, 60557, 60566,
                    60576, 60579, 60585, 60588, 60600, 60613, 60628, 60631, 60648, 60651, 60653, 60671, 60673, 60691, 60697, 60700,
                    60714, 60724, 60728, 60733, 60736, 60745, 60746, 60753, 60754, 60782, 60784, 60790, 60793, 60805, 60830, 60836,
                    60846, 60851, 60880, 60896, 60905, 60923, 60925, 60932, 60939, 60942, 60953, 60956, 60959, 60963, 60969, 60978,
                    60980, 60987, 61007, 61012, 61015, 61025, 61026, 61038, 61040, 61052, 61061, 61074, 61083, 61085, 61086, 61096,
                    61119, 61124, 61127, 61128, 61133, 61134, 61146, 61155, 61161, 61162, 61169, 61179, 61182, 61190, 61201, 61208,
                    61238, 61241, 61247, 61259, 61267, 61269, 61295, 61304, 61309, 61310, 61313, 61320, 61325, 61334, 61337, 61386,
                    61399, 61422, 61429, 61436, 61439, 61466, 61477, 61478, 61490, 61495, 61504, 61514, 61516, 61521, 61527, 61531,
                    61540, 61550, 61557, 61562, 61577, 61583, 61597, 61602, 61604, 61607, 61622, 61625, 61633, 61640, 61643, 61651,
                    61658, 61670, 61674, 61676, 61679, 61688, 61693, 61701, 61711, 61714, 61723, 61725, 61726, 61729, 61741, 61761,
                    61779, 61781, 61782, 61797, 61809, 61810, 61816, 61822, 61849, 61876, 61893, 61905, 61908, 61911, 61912, 61928,
                    61934, 61957, 61961, 61969, 61979, 61982, 62003, 62006, 62010, 62015, 62020, 62024, 62029, 62032, 62035, 62038,
                    62047, 62068, 62071, 62082, 62088, 62096, 62108, 62124, 62142, 62147, 62153, 62159, 62161, 62171, 62189, 62207,
                    62227, 62230, 62234, 62236, 62239, 62257, 62270, 62278, 62287, 62295, 62301, 62306, 62312, 62317, 62330, 62342,
                    62359, 62365, 62366, 62370, 62375, 62376, 62387, 62404, 62411, 62435, 62441, 62442, 62452, 62459, 62461, 62473,
                    62474, 62493, 62510, 62518, 62524, 62549, 62556, 62565, 62566, 62580, 62584, 62589, 62596, 62608, 62636, 62642,
                    62651, 62654, 62659, 62666, 62680, 62692, 62701, 62702, 62714, 62716, 62733, 62762, 62769, 62770, 62787, 62794,
                    62801, 62807, 62808, 62813, 62817, 62818, 62832, 62841, 62857, 62860, 62871, 62882, 62888, 62913, 62919, 62920,
                    62925, 62933, 62938, 62940, 62949, 62956, 62971, 62978, 62990, 62995, 62997, 63004, 63011, 63032, 63038, 63067,
                    63069, 63076, 63083, 63088, 63094, 63097, 63100, 63104, 63114, 63116, 63122, 63128, 63149, 63150, 63157, 63167,
                    63187, 63200, 63220, 63223, 63229, 63235, 63247, 63252, 63255, 63289, 63298, 63303, 63327, 63328, 63348, 63355,
                    63357, 63368, 63391, 63402, 63409, 63416, 63419, 63430, 63442, 63457, 63458, 63482, 63487, 63488, 63500, 63511,
                    63517, 63528, 63531, 63559, 63566, 63578, 63580, 63584, 63587, 63594, 63607, 63611, 63630, 63632, 63641, 63647,
                    63651, 63666, 63668, 63695, 63697, 63709, 63723, 63737, 63746, 63752, 63755, 63775, 63781, 63794, 63796, 63808,
                    63817, 63820, 63826, 63838, 63848, 63861, 63862, 63866, 63878, 63889, 63892, 63901, 63915, 63917, 63926, 63932,
                    63937, 63938, 63943, 63944, 63950, 63952, 63955, 63957, 63961, 63962, 63978, 63983, 63988, 64008, 64013, 64019,
                    64026, 64028, 64032, 64042, 64044, 64055, 64070, 64079, 64084, 64087, 64093, 64100, 64104, 64109, 64140, 64148,
                    64151, 64152, 64171, 64179, 64186, 64203, 64206, 64213, 64214, 64220, 64233, 64239, 64248, 64251, 64259, 64268,
                    64273, 64279, 64283, 64292, 64296, 64301, 64302, 64324, 64331, 64346, 64364, 64382, 64386, 64400, 64409, 64419,
                    64433, 64454, 64458, 64482, 64494, 64496, 64505, 64514, 64519, 64525, 64528, 64534, 64561, 64571, 64579, 64591,
                    64606, 64616, 64619, 64640, 64645, 64649, 64652, 64670, 64673, 64674, 64683, 64697, 64703, 64711, 64723, 64736,
                    64739, 64741, 64748, 64759, 64768, 64771, 64778, 64792, 64821, 64831, 64839, 64848, 64860, 64867, 64870, 64874,
                    64879, 64887, 64894, 64897, 64898, 64921, 64933, 64940, 64957, 64969, 64972, 64978, 64984, 64993, 64999, 65000,
                    65006, 65011, 65014, 65036, 65044, 65063, 65067, 65075, 65077, 65082, 65095, 65101, 65104, 65109, 65110, 65116,
                    65126, 65129, 65138, 65160, 65171, 65173, 65180, 65189, 65193, 65208, 65214, 65216, 65225, 65236, 65239, 65273,
                    65274, 65281, 65294, 65324, 65335, 65354, 65359, 65373, 65383, 65387, 65397, 65411, 65413, 65418, 65441, 65444,
                    65447, 65448, 65473, 65476, 65480, 65494, 65509, 65519, 65527, -1 };
            primitivePolynomials[16] = PrimitivePolynomialDegree17;
        }
    }

    private void applyPrimitivePolynomialDegree17() {
        if (ppmtMaxDim >= N_PRIMITIVES_UP_TO_DEGREE_17) {
            final long[] PrimitivePolynomialDegree18 = { 19, 31, 38, 61, 64, 109, 115, 118, 131, 167, 200, 241, 244, 247, 261, 265,
                    304, 314, 341, 376, 395, 405, 406, 443, 451, 458, 460, 468, 472, 482, 491, 496, 524, 536, 542, 557, 572, 580,
                    592, 656, 713, 719, 738, 749, 752, 767, 772, 782, 789, 830, 838, 916, 920, 936, 991, 998, 1016, 1024, 1053,
                    1081, 1090, 1125, 1135, 1143, 1150, 1154, 1171, 1174, 1202, 1213, 1225, 1233, 1234, 1252, 1255, 1294, 1349,
                    1354, 1378, 1383, 1387, 1392, 1402, 1420, 1428, 1437, 1448, 1459, 1473, 1507, 1516, 1528, 1573, 1592, 1597,
                    1654, 1663, 1730, 1739, 1741, 1753, 1783, 1804, 1807, 1832, 1850, 1852, 1881, 1894, 1927, 1952, 1969, 1999,
                    2018, 2047, 2066, 2068, 2093, 2105, 2116, 2149, 2201, 2228, 2245, 2246, 2283, 2288, 2320, 2326, 2386, 2392,
                    2395, 2413, 2419, 2441, 2466, 2477, 2485, 2492, 2495, 2498, 2504, 2515, 2521, 2561, 2579, 2604, 2657, 2681,
                    2691, 2731, 2739, 2765, 2790, 2802, 2819, 2891, 2905, 2911, 2912, 2921, 2924, 2945, 2952, 2972, 2986, 3000,
                    3008, 3011, 3018, 3047, 3071, 3079, 3083, 3086, 3148, 3156, 3194, 3230, 3233, 3254, 3268, 3305, 3323, 3326,
                    3343, 3345, 3382, 3413, 3420, 3434, 3453, 3472, 3488, 3503, 3506, 3518, 3532, 3543, 3547, 3573, 3574, 3587,
                    3632, 3664, 3713, 3726, 3768, 3771, 3810, 3848, 3868, 3896, 3910, 3921, 3928, 3940, 3947, 3949, 4001, 4004,
                    4022, 4026, 4036, 4073, 4093, 4099, 4120, 4136, 4156, 4176, 4182, 4191, 4198, 4252, 4259, 4261, 4294, 4341,
                    4348, 4356, 4384, 4389, 4401, 4428, 4431, 4445, 4470, 4473, 4474, 4490, 4509, 4510, 4533, 4548, 4558, 4594,
                    4596, 4603, 4610, 4619, 4630, 4652, 4658, 4682, 4708, 4736, 4753, 4760, 4781, 4784, 4799, 4807, 4808, 4842,
                    4882, 4897, 4900, 4922, 4936, 4960, 4970, 4978, 4994, 5003, 5044, 5061, 5107, 5127, 5131, 5136, 5145, 5146,
                    5175, 5218, 5223, 5248, 5294, 5299, 5301, 5311, 5343, 5344, 5396, 5406, 5410, 5424, 5447, 5461, 5468, 5475,
                    5478, 5523, 5530, 5535, 5548, 5559, 5580, 5604, 5622, 5641, 5649, 5656, 5671, 5689, 5721, 5731, 5734, 5738,
                    5740, 5748, 5791, 5801, 5807, 5822, 5829, 5834, 5847, 5854, 5878, 5882, 5919, 5947, 5981, 5982, 6025, 6028,
                    6039, 6056, 6064, 6073, 6081, 6088, 6101, 6139, 6142, 6194, 6199, 6200, 6203, 6214, 6238, 6241, 6244, 6248,
                    6256, 6266, 6271, 6275, 6295, 6315, 6364, 6368, 6377, 6400, 6430, 6477, 6480, 6490, 6526, 6539, 6556, 6584,
                    6598, 6601, 6609, 6612, 6645, 6655, 6679, 6683, 6719, 6727, 6734, 6770, 6776, 6791, 6798, 6800, 6826, 6833,
                    6836, 6858, 6882, 6901, 6923, 6967, 6979, 7022, 7060, 7064, 7083, 7093, 7094, 7120, 7125, 7142, 7151, 7165,
                    7173, 7188, 7191, 7202, 7216, 7219, 7267, 7358, 7383, 7387, 7396, 7408, 7432, 7462, 7480, 7488, 7497, 7531,
                    7561, 7582, 7591, 7612, 7677, 7687, 7701, 7705, 7712, 7721, 7736, 7756, 7777, 7783, 7792, 7797, 7802, 7808,
                    7866, 7885, 7891, 7913, 7936, 7946, 7963, 7996, 7999, 8011, 8014, 8028, 8047, 8077, 8101, 8119, 8125, 8140,
                    8152, 8209, 8212, 8231, 8258, 8275, 8303, 8308, 8312, 8345, 8346, 8370, 8402, 8411, 8414, 8424, 8435, 8452,
                    8462, 8479, 8509, 8515, 8522, 8541, 8560, 8563, 8585, 8594, 8596, 8603, 8610, 8647, 8661, 8662, 8672, 8690,
                    8696, 8739, 8783, 8811, 8813, 8821, 8856, 8877, 8885, 8897, 8898, 8934, 8955, 8972, 8977, 9038, 9045, 9071,
                    9085, 9110, 9120, 9157, 9164, 9185, 9203, 9235, 9278, 9290, 9292, 9319, 9334, 9362, 9387, 9404, 9407, 9410,
                    9422, 9478, 9512, 9535, 9604, 9616, 9622, 9631, 9656, 9710, 9721, 9728, 9733, 9738, 9774, 9791, 9803, 9805,
                    9817, 9823, 9839, 9847, 9854, 9863, 9872, 9884, 9935, 9937, 9974, 9980, 10003, 10015, 10016, 10066, 10093,
                    10118, 10132, 10135, 10151, 10158, 10169, 10172, 10180, 10187, 10189, 10192, 10197, 10211, 10214, 10256, 10278,
                    10290, 10301, 10310, 10344, 10473, 10474, 10482, 10499, 10525, 10532, 10535, 10556, 10568, 10579, 10586, 10609,
                    10612, 10650, 10665, 10685, 10686, 10688, 10711, 10728, 10748, 10751, 10781, 10805, 10823, 10838, 10857, 10865,
                    10872, 10893, 10899, 10917, 10935, 11001, 11072, 11089, 11090, 11108, 11117, 11136, 11148, 11153, 11181, 11190,
                    11201, 11204, 11216, 11237, 11259, 11279, 11282, 11304, 11312, 11332, 11341, 11354, 11356, 11365, 11377, 11417,
                    11427, 11433, 11434, 11439, 11444, 11448, 11461, 11466, 11468, 11473, 11489, 11499, 11516, 11531, 11533, 11557,
                    11572, 11608, 11635, 11638, 11644, 11684, 11702, 11740, 11749, 11754, 11764, 11767, 11784, 11789, 11795, 11798,
                    11804, 11818, 11820, 11860, 11869, 11874, 11903, 11916, 11927, 11933, 11962, 12009, 12020, 12035, 12041, 12049,
                    12065, 12072, 12098, 12100, 12107, 12167, 12202, 12207, 12233, 12269, 12290, 12304, 12314, 12340, 12416, 12425,
                    12428, 12446, 12462, 12476, 12496, 12511, 12518, 12530, 12532, 12539, 12567, 12597, 12601, 12602, 12622, 12693,
                    12707, 12713, 12716, 12734, 12763, 12765, 12799, 12843, 12853, 12865, 12866, 12919, 12926, 12936, 12944, 12959,
                    12965, 12980, 13004, 13016, 13019, 13037, 13055, 13063, 13098, 13103, 13106, 13115, 13143, 13144, 13153, 13171,
                    13184, 13193, 13199, 13207, 13217, 13227, 13232, 13276, 13283, 13307, 13317, 13330, 13345, 13363, 13387, 13426,
                    13428, 13431, 13489, 13501, 13533, 13589, 13600, 13603, 13623, 13665, 13672, 13675, 13708, 13713, 13720, 13730,
                    13735, 13767, 13773, 13792, 13843, 13891, 13946, 13967, 13976, 14017, 14030, 14041, 14054, 14058, 14060, 14063,
                    14078, 14080, 14089, 14131, 14134, 14137, 14188, 14215, 14222, 14249, 14292, 14320, 14339, 14346, 14353, 14365,
                    14384, 14414, 14428, 14435, 14483, 14486, 14506, 14513, 14520, 14526, 14528, 14533, 14576, 14599, 14613, 14639,
                    14644, 14653, 14662, 14695, 14704, 14713, 14725, 14783, 14810, 14816, 14822, 14828, 14883, 14889, 14904, 14917,
                    14927, 14941, 14946, 14952, 15010, 15012, 15033, 15041, 15047, 15066, 15068, 15072, 15095, 15150, 15152, 15175,
                    15176, 15210, 15245, 15258, 15263, 15264, 15279, 15301, 15306, 15313, 15329, 15341, 15367, 15368, 15386, 15404,
                    15422, 15436, 15444, 15467, 15498, 15503, 15528, 15534, 15536, 15559, 15565, 15583, 15599, 15602, 15616, 15626,
                    15667, 15684, 15711, 15745, 15763, 15779, 15811, 15814, 15817, 15853, 15881, 15890, 15899, 15905, 15915, 15937,
                    15950, 16002, 16041, 16042, 16052, 16121, 16132, 16136, 16150, 16153, 16180, 16190, 16192, 16195, 16210, 16225,
                    16256, 16266, 16274, 16302, 16310, 16322, 16339, 16345, 16348, 16357, 16382, 16402, 16461, 16469, 16474, 16483,
                    16497, 16523, 16534, 16550, 16564, 16582, 16591, 16609, 16642, 16648, 16656, 16662, 16665, 16678, 16701, 16713,
                    16716, 16758, 16768, 16797, 16804, 16811, 16813, 16881, 16888, 16893, 16922, 16937, 16958, 16965, 16978, 16980,
                    16989, 17011, 17014, 17023, 17063, 17081, 17084, 17099, 17126, 17129, 17138, 17164, 17170, 17203, 17206, 17224,
                    17305, 17322, 17327, 17332, 17342, 17387, 17410, 17419, 17429, 17439, 17440, 17457, 17458, 17469, 17477, 17512,
                    17518, 17532, 17536, 17559, 17569, 17641, 17667, 17674, 17693, 17710, 17729, 17754, 17763, 17780, 17784, 17790,
                    17830, 17851, 17868, 17879, 17896, 17901, 17916, 17935, 17953, 17991, 18009, 18016, 18034, 18061, 18070, 18079,
                    18132, 18145, 18169, 18170, 18190, 18192, 18201, 18228, 18243, 18260, 18279, 18285, 18297, 18300, 18321, 18344,
                    18358, 18364, 18376, 18390, 18399, 18400, 18427, 18443, 18487, 18493, 18508, 18532, 18535, 18580, 18587, 18606,
                    18659, 18662, 18679, 18694, 18697, 18706, 18708, 18728, 18731, 18733, 18751, 18766, 18773, 18796, 18811, 18823,
                    18832, 18854, 18858, 18883, 18900, 18967, 18974, 18990, 19009, 19055, 19058, 19074, 19079, 19086, 19128, 19145,
                    19163, 19175, 19189, 19196, 19204, 19213, 19237, 19276, 19287, 19291, 19293, 19304, 19371, 19379, 19381, 19405,
                    19417, 19420, 19424, 19462, 19474, 19492, 19501, 19504, 19516, 19519, 19534, 19555, 19597, 19600, 19615, 19621,
                    19646, 19651, 19693, 19702, 19726, 19754, 19759, 19764, 19788, 19793, 19836, 19855, 19858, 19880, 19883, 19917,
                    19918, 19936, 19946, 19954, 19979, 19993, 19994, 20006, 20017, 20032, 20038, 20050, 20080, 20135, 20142, 20159,
                    20173, 20186, 20229, 20234, 20263, 20270, 20299, 20304, 20319, 20329, 20337, 20353, 20401, 20434, 20436, 20473,
                    20488, 20512, 20515, 20517, 20530, 20571, 20574, 20589, 20592, 20611, 20613, 20628, 20637, 20638, 20719, 20724,
                    20772, 20796, 20799, 20816, 20841, 20844, 20862, 20865, 20902, 20938, 20957, 20971, 20973, 20981, 20982, 20985,
                    20991, 21012, 21022, 21026, 21028, 21050, 21075, 21077, 21078, 21106, 21122, 21127, 21155, 21167, 21169, 21204,
                    21230, 21256, 21285, 21312, 21329, 21351, 21372, 21376, 21424, 21448, 21465, 21495, 21509, 21550, 21562, 21575,
                    21579, 21582, 21600, 21615, 21617, 21624, 21633, 21636, 21645, 21654, 21660, 21667, 21702, 21714, 21719, 21736,
                    21767, 21781, 21795, 21841, 21853, 21867, 21878, 21891, 21897, 21954, 21956, 21965, 21977, 21978, 21983, 22002,
                    22013, 22014, 22054, 22058, 22066, 22085, 22103, 22138, 22140, 22153, 22164, 22167, 22189, 22202, 22212, 22234,
                    22239, 22245, 22272, 22308, 22318, 22320, 22338, 22350, 22374, 22388, 22391, 22422, 22426, 22441, 22481, 22488,
                    22500, 22517, 22531, 22552, 22574, 22579, 22582, 22586, 22594, 22611, 22614, 22700, 22711, 22749, 22759, 22774,
                    22777, 22780, 22783, 22785, 22840, 22878, 22884, 22888, 22927, 22941, 22951, 22958, 22980, 23007, 23032, 23041,
                    23044, 23059, 23065, 23072, 23137, 23144, 23155, 23162, 23167, 23183, 23192, 23197, 23216, 23221, 23228, 23233,
                    23234, 23251, 23257, 23258, 23288, 23302, 23341, 23373, 23402, 23409, 23428, 23435, 23440, 23449, 23459, 23473,
                    23500, 23511, 23548, 23560, 23563, 23594, 23601, 23625, 23640, 23650, 23662, 23692, 23726, 23738, 23743, 23755,
                    23760, 23799, 23848, 23859, 23876, 23897, 23907, 23919, 23921, 23931, 23933, 23943, 23957, 23986, 24020, 24043,
                    24064, 24082, 24084, 24088, 24117, 24149, 24165, 24223, 24247, 24259, 24283, 24296, 24336, 24342, 24389, 24394,
                    24414, 24424, 24441, 24491, 24502, 24534, 24540, 24543, 24549, 24567, 24583, 24693, 24704, 24719, 24749, 24799,
                    24830, 24835, 24907, 24917, 24940, 24943, 24958, 24962, 24964, 24968, 25015, 25024, 25027, 25033, 25041, 25047,
                    25057, 25082, 25093, 25105, 25112, 25117, 25131, 25139, 25194, 25204, 25244, 25247, 25253, 25258, 25275, 25320,
                    25338, 25343, 25351, 25358, 25372, 25399, 25408, 25432, 25442, 25462, 25465, 25468, 25482, 25489, 25490, 25505,
                    25511, 25512, 25552, 25555, 25595, 25636, 25672, 25685, 25696, 25720, 25730, 25735, 25753, 25756, 25769, 25821,
                    25825, 25840, 25860, 25869, 25872, 25956, 25973, 25994, 26002, 26020, 26023, 26041, 26044, 26069, 26097, 26113,
                    26138, 26167, 26216, 26230, 26243, 26245, 26263, 26264, 26269, 26311, 26346, 26354, 26365, 26368, 26398, 26401,
                    26426, 26436, 26443, 26448, 26458, 26464, 26476, 26503, 26504, 26515, 26531, 26548, 26570, 26589, 26594, 26608,
                    26623, 26678, 26690, 26709, 26725, 26744, 26763, 26765, 26768, 26784, 26802, 26814, 26856, 26861, 26862, 26894,
                    26908, 26915, 26929, 26930, 26939, 26941, 26987, 27023, 27066, 27079, 27086, 27091, 27113, 27157, 27174, 27188,
                    27191, 27197, 27206, 27210, 27245, 27254, 27383, 27387, 27416, 27422, 27472, 27478, 27548, 27579, 27582, 27589,
                    27593, 27613, 27649, 27717, 27729, 27751, 27757, 27766, 27791, 27794, 27854, 27856, 27866, 27875, 27902, 27916,
                    27924, 27933, 27940, 27947, 27969, 27981, 27996, 28005, 28030, 28064, 28102, 28108, 28120, 28125, 28130, 28142,
                    28149, 28165, 28169, 28177, 28211, 28213, 28220, 28261, 28271, 28299, 28326, 28330, 28350, 28355, 28367, 28376,
                    28379, 28381, 28447, 28465, 28471, 28472, 28478, 28480, 28489, 28514, 28525, 28537, 28550, 28559, 28595, 28601,
                    28629, 28657, 28667, 28711, 28718, 28730, 28732, 28777, 28801, 28822, 28855, 28859, 28869, 28893, 28904, 28922,
                    28930, 28949, 28972, 29002, 29009, 29061, 29068, 29107, 29119, 29142, 29146, 29152, 29200, 29206, 29245, 29254,
                    29258, 29260, 29268, 29281, 29306, 29317, 29324, 29342, 29375, 29384, 29389, 29411, 29413, 29446, 29457, 29458,
                    29483, 29488, 29494, 29506, 29553, 29569, 29587, 29590, 29600, 29612, 29665, 29722, 29745, 29746, 29752, 29775,
                    29778, 29800, 29829, 29830, 29877, 29881, 29884, 29895, 29899, 29916, 29923, 29935, 29955, 29962, 29969, 29997,
                    30032, 30041, 30048, 30060, 30063, 30105, 30122, 30141, 30142, 30153, 30171, 30192, 30197, 30223, 30235, 30241,
                    30256, 30291, 30293, 30300, 30331, 30349, 30361, 30367, 30392, 30405, 30410, 30415, 30439, 30446, 30501, 30526,
                    30531, 30537, 30546, 30568, 30581, 30588, 30598, 30607, 30609, 30616, 30626, 30628, 30638, 30658, 30663, 30675,
                    30698, 30708, 30712, 30727, 30736, 30748, 30758, 30775, 30793, 30802, 30820, 30823, 30827, 30848, 30893, 30906,
                    30950, 30956, 30996, 31015, 31016, 31034, 31099, 31123, 31145, 31166, 31168, 31174, 31216, 31219, 31238, 31256,
                    31304, 31315, 31324, 31364, 31379, 31382, 31412, 31451, 31487, 31509, 31510, 31520, 31535, 31564, 31585, 31586,
                    31600, 31643, 31646, 31649, 31650, 31674, 31679, 31684, 31688, 31706, 31735, 31754, 31761, 31774, 31807, 31812,
                    31821, 31855, 31919, 31934, 31956, 31965, 31975, 31981, 31999, 32014, 32022, 32049, 32055, 32082, 32087, 32100,
                    32104, 32109, 32138, 32140, 32148, 32161, 32162, 32174, 32196, 32220, 32223, 32267, 32272, 32308, 32315, 32317,
                    32347, 32360, 32394, 32411, 32420, 32423, 32455, 32456, 32510, 32517, 32541, 32552, 32555, 32580, 32598, 32654,
                    32662, 32665, 32678, 32682, 32687, 32692, 32710, 32791, 32792, 32813, 32826, 32831, 32836, 32843, 32854, 32893,
                    32897, 32912, 32951, 32963, 32970, 32975, 33028, 33052, 33061, 33083, 33103, 33117, 33139, 33151, 33162, 33209,
                    33210, 33223, 33227, 33241, 33263, 33341, 33344, 33356, 33390, 33398, 33423, 33428, 33444, 33454, 33479, 33510,
                    33514, 33528, 33548, 33566, 33570, 33593, 33599, 33619, 33635, 33659, 33680, 33699, 33713, 33758, 33771, 33779,
                    33800, 33829, 33836, 33844, 33856, 33859, 33873, 33874, 33929, 33944, 33965, 33978, 33986, 34006, 34025, 34106,
                    34126, 34171, 34189, 34204, 34260, 34270, 34280, 34298, 34313, 34321, 34333, 34370, 34376, 34387, 34389, 34417,
                    34429, 34440, 34443, 34451, 34470, 34484, 34493, 34496, 34514, 34526, 34535, 34585, 34591, 34592, 34602, 34607,
                    34644, 34657, 34675, 34681, 34693, 34706, 34745, 34759, 34765, 34774, 34780, 34794, 34807, 34808, 34835, 34842,
                    34865, 34907, 34928, 34949, 34961, 34964, 34987, 34990, 35019, 35069, 35077, 35090, 35152, 35173, 35174, 35202,
                    35214, 35219, 35226, 35235, 35255, 35310, 35318, 35321, 35333, 35337, 35346, 35373, 35414, 35478, 35497, 35503,
                    35512, 35515, 35526, 35577, 35585, 35592, 35615, 35616, 35622, 35634, 35657, 35672, 35708, 35772, 35790, 35804,
                    35811, 35814, 35837, 35846, 35873, 35883, 35918, 35920, 35925, 35926, 35945, 35956, 36003, 36023, 36024, 36059,
                    36061, 36072, 36075, 36103, 36110, 36131, 36151, 36160, 36199, 36223, 36227, 36234, 36236, 36284, 36319, 36332,
                    36343, 36373, 36401, 36413, 36433, 36459, 36480, 36485, 36510, 36513, 36523, 36528, 36537, 36558, 36563, 36576,
                    36608, 36611, 36617, 36653, 36716, 36721, 36733, 36761, 36783, 36786, 36795, 36800, 36810, 36812, 36817, 36818,
                    36851, 36853, 36868, 36889, 36890, 36896, 36905, 36919, 36938, 36946, 36951, 36952, 36964, 36968, 36979, 36981,
                    37021, 37026, 37075, 37077, 37108, 37112, 37150, 37154, 37156, 37163, 37178, 37183, 37185, 37191, 37198, 37203,
                    37225, 37243, 37252, 37262, 37264, 37276, 37327, 37355, 37403, 37415, 37422, 37448, 37451, 37478, 37484, 37525,
                    37553, 37640, 37645, 37658, 37667, 37708, 37723, 37732, 37747, 37753, 37772, 37789, 37817, 37826, 37831, 37845,
                    37855, 37859, 37866, 37880, 37885, 37897, 37906, 37948, 37954, 37978, 37980, 37990, 38001, 38020, 38029, 38047,
                    38063, 38077, 38110, 38116, 38119, 38176, 38186, 38194, 38218, 38241, 38247, 38261, 38268, 38277, 38287, 38295,
                    38299, 38301, 38305, 38312, 38315, 38317, 38343, 38344, 38350, 38358, 38364, 38371, 38373, 38377, 38392, 38407,
                    38408, 38421, 38438, 38442, 38464, 38473, 38484, 38491, 38510, 38518, 38531, 38537, 38538, 38567, 38594, 38647,
                    38651, 38654, 38686, 38702, 38751, 38821, 38825, 38853, 38863, 38882, 38884, 38932, 38941, 38952, 38960, 38980,
                    38995, 39004, 39013, 39017, 39026, 39056, 39121, 39124, 39133, 39157, 39181, 39187, 39212, 39223, 39250, 39265,
                    39320, 39330, 39335, 39353, 39371, 39385, 39421, 39432, 39443, 39450, 39455, 39468, 39473, 39488, 39497, 39521,
                    39531, 39542, 39585, 39605, 39658, 39677, 39703, 39726, 39731, 39745, 39776, 39796, 39809, 39833, 39850, 39869,
                    39882, 39906, 39918, 39929, 39947, 39985, 39986, 40023, 40024, 40039, 40053, 40079, 40084, 40087, 40094, 40100,
                    40112, 40129, 40132, 40142, 40154, 40177, 40178, 40202, 40219, 40303, 40305, 40311, 40336, 40342, 40358, 40381,
                    40417, 40438, 40463, 40471, 40472, 40488, 40491, 40525, 40526, 40550, 40553, 40562, 40568, 40580, 40608, 40613,
                    40652, 40703, 40712, 40720, 40723, 40746, 40759, 40765, 40771, 40778, 40788, 40804, 40835, 40841, 40907, 40934,
                    40955, 40986, 41007, 41016, 41034, 41041, 41063, 41084, 41093, 41097, 41111, 41118, 41121, 41141, 41160, 41207,
                    41214, 41255, 41284, 41287, 41308, 41330, 41336, 41437, 41444, 41456, 41481, 41489, 41499, 41555, 41561, 41562,
                    41571, 41592, 41631, 41661, 41682, 41710, 41724, 41727, 41730, 41744, 41769, 41795, 41798, 41804, 41826, 41852,
                    41868, 41910, 41914, 41942, 41969, 41975, 41976, 41994, 42002, 42037, 42056, 42079, 42085, 42089, 42123, 42128,
                    42140, 42149, 42156, 42186, 42191, 42205, 42221, 42222, 42230, 42233, 42241, 42247, 42248, 42254, 42259, 42268,
                    42295, 42304, 42322, 42334, 42355, 42361, 42373, 42374, 42398, 42411, 42443, 42446, 42463, 42479, 42482, 42484,
                    42498, 42534, 42537, 42557, 42570, 42599, 42600, 42633, 42639, 42672, 42681, 42687, 42704, 42713, 42714, 42716,
                    42726, 42730, 42737, 42752, 42757, 42772, 42809, 42853, 42857, 42902, 42912, 42930, 42936, 42956, 42959, 42978,
                    43001, 43011, 43047, 43059, 43094, 43124, 43218, 43220, 43240, 43258, 43263, 43265, 43290, 43346, 43364, 43373,
                    43401, 43416, 43443, 43458, 43497, 43506, 43545, 43558, 43576, 43579, 43624, 43630, 43637, 43654, 43660, 43696,
                    43706, 43725, 43733, 43753, 43756, 43761, 43768, 43812, 43822, 43841, 43861, 43868, 43877, 43878, 43908, 43917,
                    43936, 43954, 43963, 43977, 44008, 44013, 44026, 44028, 44045, 44046, 44053, 44091, 44101, 44129, 44139, 44172,
                    44203, 44211, 44240, 44259, 44305, 44383, 44384, 44389, 44390, 44432, 44435, 44468, 44472, 44483, 44497, 44550,
                    44577, 44584, 44601, 44674, 44683, 44688, 44694, 44703, 44713, 44721, 44722, 44733, 44746, 44748, 44760, 44765,
                    44766, 44775, 44794, 44822, 44841, 44859, 44894, 44900, 44904, 44946, 44967, 44994, 44999, 45013, 45017, 45020,
                    45027, 45029, 45066, 45074, 45080, 45090, 45101, 45110, 45113, 45134, 45136, 45148, 45158, 45176, 45181, 45185,
                    45188, 45203, 45209, 45231, 45234, 45239, 45246, 45260, 45284, 45301, 45333, 45350, 45354, 45446, 45458, 45518,
                    45566, 45569, 45575, 45576, 45587, 45615, 45661, 45798, 45829, 45847, 45858, 45864, 45899, 45904, 45919, 45974,
                    45987, 45989, 46008, 46036, 46039, 46043, 46045, 46079, 46084, 46091, 46105, 46117, 46141, 46147, 46162, 46184,
                    46197, 46207, 46214, 46218, 46237, 46247, 46251, 46327, 46333, 46342, 46366, 46376, 46384, 46404, 46478, 46511,
                    46540, 46638, 46700, 46706, 46745, 46757, 46772, 46776, 46790, 46794, 46804, 46820, 46849, 46870, 46876, 46903,
                    46904, 46907, 46932, 46955, 46963, 46991, 47029, 47036, 47054, 47099, 47106, 47132, 47145, 47151, 47156, 47171,
                    47188, 47201, 47219, 47226, 47241, 47252, 47261, 47295, 47297, 47333, 47337, 47360, 47377, 47378, 47384, 47389,
                    47417, 47420, 47426, 47443, 47504, 47507, 47510, 47526, 47550, 47552, 47557, 47564, 47591, 47610, 47621, 47639,
                    47646, 47676, 47682, 47732, 47741, 47751, 47757, 47786, 47803, 47828, 47848, 47853, 47856, 47862, 47874, 47883,
                    47916, 47934, 47951, 47979, 48010, 48027, 48046, 48060, 48065, 48075, 48096, 48119, 48173, 48176, 48186, 48213,
                    48223, 48224, 48229, 48241, 48281, 48284, 48291, 48308, 48340, 48344, 48368, 48400, 48406, 48409, 48421, 48448,
                    48453, 48457, 48478, 48493, 48527, 48583, 48597, 48641, 48668, 48671, 48682, 48709, 48719, 48731, 48780, 48791,
                    48797, 48807, 48811, 48814, 48821, 48836, 48846, 48848, 48854, 48874, 48908, 48929, 48941, 48954, 48961, 48962,
                    48985, 49001, 49007, 49015, 49035, 49062, 49066, 49085, 49091, 49094, 49127, 49134, 49183, 49221, 49231, 49250,
                    49252, 49270, 49297, 49313, 49340, 49365, 49379, 49386, 49391, 49417, 49438, 49444, 49453, 49454, 49480, 49485,
                    49498, 49513, 49527, 49531, 49564, 49588, 49609, 49617, 49645, 49646, 49654, 49697, 49718, 49741, 49754, 49756,
                    49833, 49834, 49879, 49942, 49946, 49982, 49994, 50001, 50023, 50047, 50120, 50133, 50137, 50140, 50156, 50199,
                    50205, 50215, 50216, 50224, 50229, 50233, 50251, 50281, 50326, 50332, 50348, 50359, 50374, 50404, 50443, 50488,
                    50493, 50505, 50511, 50525, 50542, 50550, 50560, 50570, 50587, 50593, 50611, 50649, 50656, 50685, 50686, 50692,
                    50702, 50704, 50709, 50720, 50726, 50729, 50737, 50758, 50772, 50849, 50850, 50881, 50901, 50906, 50915, 50961,
                    50992, 50997, 51001, 51004, 51033, 51045, 51052, 51064, 51085, 51127, 51139, 51153, 51181, 51182, 51194, 51196,
                    51200, 51239, 51240, 51254, 51260, 51342, 51370, 51372, 51377, 51398, 51402, 51407, 51410, 51412, 51419, 51428,
                    51431, 51460, 51475, 51477, 51478, 51482, 51511, 51518, 51568, 51607, 51618, 51630, 51655, 51673, 51683, 51709,
                    51716, 51774, 51829, 51830, 51839, 51849, 51869, 51886, 51905, 51906, 51915, 51948, 51988, 51998, 52004, 52026,
                    52036, 52082, 52127, 52145, 52157, 52165, 52193, 52211, 52213, 52218, 52220, 52238, 52240, 52243, 52250, 52279,
                    52285, 52306, 52370, 52432, 52457, 52466, 52514, 52557, 52560, 52585, 52586, 52603, 52650, 52672, 52681, 52692,
                    52735, 52760, 52772, 52781, 52793, 52796, 52807, 52821, 52849, 52866, 52868, 52872, 52875, 52886, 52890, 52896,
                    52925, 52931, 52937, 52938, 52952, 52979, 53013, 53023, 53027, 53033, 53039, 53041, 53048, 53073, 53095, 53099,
                    53120, 53154, 53168, 53192, 53205, 53206, 53234, 53246, 53253, 53263, 53275, 53313, 53331, 53349, 53367, 53383,
                    53392, 53404, 53407, 53411, 53417, 53425, 53443, 53469, 53480, 53508, 53535, 53536, 53542, 53563, 53577, 53591,
                    53607, 53626, 53628, 53638, 53675, 53685, 53692, 53703, 53722, 53738, 53748, 53752, 53764, 53781, 53788, 53791,
                    53816, 53824, 53830, 53839, 53844, 53875, 53921, 53922, 53927, 53948, 53956, 53990, 53994, 54001, 54016, 54019,
                    54070, 54074, 54088, 54102, 54111, 54130, 54152, 54170, 54172, 54211, 54218, 54228, 54251, 54253, 54268, 54271,
                    54274, 54288, 54314, 54319, 54321, 54324, 54341, 54353, 54366, 54370, 54420, 54423, 54434, 54440, 54454, 54495,
                    54501, 54526, 54639, 54653, 54667, 54678, 54700, 54717, 54780, 54801, 54813, 54814, 54850, 54883, 54907, 54919,
                    54931, 54933, 54991, 55003, 55009, 55030, 55034, 55039, 55048, 55059, 55061, 55068, 55077, 55122, 55149, 55157,
                    55158, 55178, 55222, 55234, 55236, 55251, 55269, 55270, 55284, 55309, 55322, 55334, 55340, 55348, 55377, 55430,
                    55433, 55441, 55470, 55535, 55561, 55567, 55597, 55630, 55648, 55653, 55657, 55665, 55678, 55684, 55691, 55693,
                    55702, 55708, 55715, 55739, 55761, 55767, 55768, 55774, 55787, 55811, 55835, 55894, 55897, 55904, 55931, 55950,
                    55961, 55973, 56078, 56099, 56105, 56119, 56133, 56168, 56191, 56202, 56209, 56215, 56232, 56240, 56260, 56270,
                    56278, 56281, 56288, 56303, 56308, 56312, 56320, 56326, 56392, 56395, 56403, 56419, 56428, 56450, 56452, 56485,
                    56486, 56492, 56498, 56510, 56512, 56524, 56530, 56545, 56551, 56565, 56580, 56587, 56589, 56592, 56611, 56623,
                    56635, 56646, 56660, 56680, 56686, 56716, 56749, 56790, 56809, 56869, 56884, 56887, 56893, 56906, 56932, 56959,
                    56965, 56983, 57018, 57023, 57025, 57032, 57040, 57046, 57071, 57091, 57094, 57108, 57122, 57127, 57168, 57183,
                    57184, 57193, 57202, 57235, 57237, 57251, 57289, 57349, 57359, 57374, 57377, 57387, 57415, 57440, 57446, 57450,
                    57469, 57491, 57503, 57507, 57542, 57569, 57599, 57601, 57607, 57608, 57625, 57644, 57662, 57664, 57674, 57698,
                    57700, 57709, 57724, 57728, 57740, 57745, 57751, 57774, 57782, 57796, 57803, 57823, 57863, 57894, 57925, 57971,
                    58013, 58020, 58032, 58038, 58061, 58069, 58080, 58089, 58107, 58121, 58130, 58146, 58190, 58195, 58202, 58237,
                    58253, 58299, 58302, 58327, 58328, 58364, 58367, 58375, 58394, 58405, 58417, 58424, 58430, 58452, 58466, 58468,
                    58495, 58501, 58544, 58571, 58609, 58610, 58616, 58619, 58641, 58642, 58648, 58660, 58675, 58678, 58690, 58735,
                    58760, 58766, 58799, 58825, 58836, 58855, 58864, 58910, 58937, 58943, 58952, 58965, 58988, 58994, 58999, 59006,
                    59029, 59033, 59069, 59096, 59106, 59123, 59130, 59152, 59162, 59180, 59183, 59195, 59205, 59210, 59217, 59218,
                    59236, 59267, 59298, 59318, 59321, 59327, 59354, 59363, 59377, 59389, 59403, 59420, 59444, 59471, 59476, 59483,
                    59502, 59509, 59538, 59556, 59571, 59592, 59610, 59654, 59677, 59699, 59716, 59719, 59737, 59747, 59789, 59807,
                    59811, 59818, 59825, 59826, 59832, 59858, 59860, 59873, 59876, 59907, 59928, 59933, 59938, 59940, 59958, 59984,
                    60020, 60024, 60033, 60034, 60063, 60070, 60087, 60091, 60105, 60126, 60132, 60154, 60174, 60181, 60198, 60212,
                    60247, 60254, 60275, 60284, 60317, 60318, 60346, 60360, 60380, 60389, 60393, 60401, 60407, 60408, 60462, 60484,
                    60505, 60521, 60527, 60532, 60545, 60546, 60555, 60563, 60586, 60626, 60637, 60665, 60673, 60685, 60703, 60734,
                    60742, 60790, 60830, 60848, 60857, 60871, 60875, 60913, 60923, 60972, 60989, 61001, 61002, 61019, 61022, 61059,
                    61065, 61066, 61071, 61076, 61141, 61148, 61167, 61190, 61218, 61247, 61256, 61289, 61292, 61307, 61319, 61353,
                    61362, 61379, 61382, 61433, 61434, 61454, 61461, 61475, 61481, 61534, 61547, 61564, 61568, 61588, 61604, 61626,
                    61645, 61646, 61654, 61663, 61669, 61702, 61705, 61714, 61739, 61744, 61749, 61776, 61786, 61822, 61826, 61862,
                    61865, 61868, 61879, 61885, 61900, 61905, 61906, 61962, 61967, 61972, 61976, 62027, 62048, 62051, 62066, 62081,
                    62108, 62178, 62189, 62224, 62229, 62282, 62318, 62320, 62329, 62341, 62353, 62359, 62382, 62389, 62396, 62425,
                    62455, 62484, 62494, 62507, 62512, 62550, 62577, 62590, 62599, 62617, 62679, 62702, 62745, 62772, 62794, 62835,
                    62842, 62847, 62857, 62894, 62911, 62928, 62954, 62964, 62977, 62984, 63007, 63017, 63058, 63067, 63085, 63150,
                    63170, 63184, 63210, 63215, 63217, 63229, 63237, 63259, 63265, 63271, 63286, 63298, 63343, 63355, 63361, 63362,
                    63379, 63397, 63415, 63421, 63463, 63491, 63527, 63545, 63553, 63560, 63563, 63566, 63589, 63599, 63601, 63608,
                    63620, 63654, 63703, 63719, 63758, 63769, 63782, 63803, 63811, 63851, 63854, 63872, 63878, 63895, 63917, 63930,
                    63935, 63955, 63964, 63967, 63980, 63985, 63992, 64021, 64026, 64044, 64055, 64056, 64061, 64067, 64081, 64100,
                    64109, 64112, 64118, 64124, 64145, 64151, 64203, 64208, 64236, 64254, 64261, 64280, 64296, 64313, 64314, 64316,
                    64348, 64352, 64355, 64361, 64388, 64398, 64403, 64419, 64421, 64428, 64453, 64463, 64475, 64511, 64537, 64576,
                    64579, 64581, 64606, 64615, 64649, 64655, 64664, 64667, 64712, 64715, 64718, 64741, 64766, 64780, 64788, 64791,
                    64795, 64798, 64816, 64819, 64822, 64833, 64836, 64876, 64891, 64907, 64943, 64965, 64977, 64999, 65034, 65036,
                    65089, 65107, 65110, 65120, 65132, 65143, 65150, 65177, 65201, 65214, 65234, 65240, 65243, 65259, 65276, 65287,
                    65306, 65311, 65321, 65350, 65364, 65451, 65488, 65510, 65528, 65533, 65553, 65563, 65579, 65621, 65625, 65635,
                    65644, 65690, 65696, 65705, 65716, 65725, 65733, 65740, 65758, 65786, 65806, 65834, 65844, 65866, 65895, 65902,
                    65953, 65954, 65983, 65985, 66003, 66010, 66025, 66050, 66074, 66076, 66097, 66132, 66158, 66175, 66185, 66199,
                    66206, 66222, 66227, 66241, 66272, 66324, 66424, 66460, 66464, 66481, 66482, 66523, 66526, 66532, 66539, 66541,
                    66573, 66582, 66591, 66615, 66622, 66629, 66641, 66712, 66724, 66731, 66745, 66759, 66808, 66814, 66821, 66831,
                    66834, 66879, 66884, 66899, 66901, 66924, 66929, 66942, 66985, 66986, 66994, 67066, 67075, 67099, 67130, 67173,
                    67183, 67186, 67197, 67201, 67208, 67221, 67238, 67252, 67256, 67270, 67288, 67318, 67329, 67349, 67369, 67392,
                    67435, 67473, 67496, 67507, 67514, 67536, 67555, 67598, 67615, 67625, 67645, 67651, 67663, 67672, 67682, 67699,
                    67718, 67780, 67795, 67804, 67818, 67832, 67941, 67984, 68044, 68059, 68062, 68065, 68071, 68072, 68085, 68090,
                    68101, 68102, 68106, 68164, 68191, 68192, 68195, 68209, 68245, 68246, 68274, 68293, 68294, 68322, 68353, 68359,
                    68377, 68401, 68422, 68434, 68443, 68445, 68476, 68492, 68504, 68525, 68540, 68543, 68555, 68563, 68576, 68581,
                    68617, 68631, 68656, 68666, 68714, 68743, 68761, 68764, 68767, 68768, 68786, 68788, 68848, 68851, 68914, 68946,
                    68952, 68973, 69001, 69016, 69035, 69037, 69049, 69052, 69058, 69063, 69075, 69112, 69117, 69124, 69127, 69134,
                    69139, 69161, 69196, 69224, 69238, 69301, 69316, 69325, 69334, 69347, 69359, 69381, 69388, 69393, 69409, 69422,
                    69453, 69459, 69481, 69487, 69490, 69495, 69501, 69511, 69512, 69532, 69542, 69560, 69565, 69571, 69611, 69633,
                    69640, 69667, 69669, 69679, 69688, 69699, 69756, 69759, 69770, 69772, 69826, 69840, 69846, 69868, 69885, 69946,
                    69966, 69973, 69977, 69980, 69984, 69994, 70024, 70044, 70053, 70086, 70113, 70120, 70131, 70150, 70178, 70198,
                    70227, 70285, 70288, 70291, 70309, 70334, 70339, 70381, 70413, 70414, 70481, 70488, 70493, 70515, 70540, 70543,
                    70558, 70568, 70582, 70593, 70596, 70613, 70620, 70647, 70666, 70710, 70719, 70721, 70722, 70742, 70757, 70764,
                    70781, 70795, 70803, 70809, 70821, 70846, 70882, 70887, 70940, 70961, 70996, 71012, 71046, 71057, 71060, 71063,
                    71079, 71080, 71111, 71118, 71129, 71136, 71142, 71145, 71151, 71163, 71218, 71223, 71232, 71237, 71272, 71323,
                    71339, 71341, 71349, 71368, 71379, 71395, 71407, 71421, 71430, 71436, 71454, 71467, 71472, 71478, 71481, 71501,
                    71519, 71530, 71550, 71573, 71593, 71608, 71619, 71622, 71636, 71650, 71655, 71685, 71703, 71714, 71748, 71758,
                    71766, 71772, 71785, 71794, 71796, 71803, 71809, 71858, 71863, 71878, 71895, 71899, 71906, 71908, 71917, 71952,
                    71957, 71961, 71973, 71980, 71997, 72017, 72060, 72091, 72097, 72166, 72172, 72183, 72203, 72205, 72213, 72214,
                    72230, 72239, 72248, 72251, 72280, 72313, 72338, 72340, 72343, 72368, 72371, 72426, 72439, 72446, 72494, 72519,
                    72573, 72577, 72578, 72614, 72628, 72640, 72676, 72708, 72717, 72766, 72786, 72788, 72795, 72797, 72798, 72808,
                    72826, 72835, 72837, 72865, 72871, 72872, 72898, 72907, 72909, 72917, 72934, 72987, 73000, 73005, 73014, 73023,
                    73025, 73074, 73076, 73113, 73116, 73140, 73149, 73186, 73188, 73191, 73205, 73215, 73216, 73239, 73294, 73311,
                    73330, 73366, 73379, 73386, 73391, 73418, 73438, 73476, 73503, 73509, 73534, 73545, 73548, 73563, 73575, 73582,
                    73603, 73609, 73610, 73645, 73646, 73678, 73685, 73708, 73747, 73765, 73797, 73810, 73819, 73825, 73845, 73855,
                    73866, 73890, 73919, 73922, 73936, 73939, 73964, 73976, 73984, 73990, 74013, 74014, 74020, 74035, 74038, 74069,
                    74119, 74123, 74126, 74153, 74179, 74200, 74210, 74216, 74250, 74263, 74264, 74279, 74285, 74294, 74298, 74303,
                    74306, 74366, 74369, 74403, 74406, 74442, 74450, 74452, 74459, 74461, 74477, 74478, 74489, 74504, 74528, 74533,
                    74538, 74551, 74580, 74589, 74590, 74593, 74608, 74611, 74617, 74623, 74648, 74704, 74735, 74737, 74738, 74779,
                    74781, 74809, 74812, 74827, 74868, 74871, 74905, 74930, 74962, 74990, 74995, 75007, 75012, 75030, 75033, 75039,
                    75055, 75078, 75120, 75130, 75159, 75163, 75170, 75193, 75228, 75244, 75249, 75328, 75357, 75373, 75392, 75428,
                    75438, 75469, 75487, 75494, 75515, 75526, 75532, 75543, 75549, 75559, 75563, 75568, 75598, 75612, 75622, 75640,
                    75683, 75689, 75690, 75692, 75752, 75766, 75769, 75791, 75794, 75806, 75809, 75819, 75834, 75895, 75908, 75951,
                    75960, 75965, 75974, 75978, 75998, 76001, 76007, 76016, 76091, 76119, 76135, 76170, 76189, 76214, 76225, 76235,
                    76259, 76261, 76262, 76266, 76286, 76289, 76310, 76319, 76364, 76375, 76392, 76422, 76450, 76496, 76521, 76522,
                    76527, 76536, 76541, 76553, 76568, 76574, 76584, 76604, 76607, 76609, 76669, 76683, 76745, 76753, 76754, 76756,
                    76760, 76782, 76821, 76828, 76849, 76850, 76870, 76874, 76898, 76903, 76910, 76921, 76968, 77013, 77027, 77042,
                    77059, 77062, 77074, 77076, 77110, 77113, 77131, 77157, 77158, 77216, 77246, 77258, 77281, 77284, 77294, 77302,
                    77306, 77315, 77352, 77360, 77365, 77370, 77378, 77380, 77407, 77408, 77414, 77444, 77454, 77481, 77482, 77531,
                    77544, 77552, 77569, 77579, 77610, 77612, 77623, 77649, 77650, 77661, 77675, 77686, 77742, 77791, 77792, 77807,
                    77815, 77842, 77847, 77899, 77901, 77904, 77914, 77925, 77950, 77959, 77994, 78004, 78043, 78052, 78061, 78064,
                    78084, 78094, 78099, 78129, 78142, 78149, 78153, 78171, 78173, 78174, 78195, 78201, 78202, 78241, 78247, 78274,
                    78276, 78303, 78307, 78328, 78344, 78362, 78367, 78368, 78409, 78412, 78417, 78430, 78433, 78440, 78451, 78453,
                    78458, 78467, 78479, 78507, 78518, 78535, 78542, 78549, 78554, 78556, 78583, 78590, 78615, 78635, 78649, 78684,
                    78691, 78705, 78724, 78755, 78770, 78772, 78818, 78837, 78844, 78849, 78909, 78915, 78941, 78958, 78975, 78979,
                    78986, 79029, 79030, 79044, 79048, 79056, 79095, 79099, 79121, 79150, 79196, 79199, 79220, 79229, 79263, 79264,
                    79296, 79302, 79354, 79387, 79399, 79435, 79525, 79540, 79552, 79562, 79576, 79579, 79605, 79618, 79648, 79654,
                    79666, 79686, 79697, 79700, 79723, 79749, 79750, 79764, 79771, 79774, 79780, 79789, 79798, 79821, 79850, 79864,
                    79898, 79904, 79934, 79936, 79942, 79945, 79963, 79987, 80057, 80077, 80078, 80080, 80102, 80106, 80111, 80152,
                    80155, 80173, 80186, 80203, 80223, 80233, 80258, 80281, 80318, 80326, 80330, 80337, 80344, 80365, 80383, 80389,
                    80413, 80441, 80449, 80461, 80467, 80476, 80507, 80516, 80519, 80568, 80571, 80579, 80586, 80599, 80610, 80629,
                    80642, 80690, 80713, 80716, 80737, 80743, 80750, 80786, 80811, 80834, 80839, 80840, 80846, 80848, 80876, 80911,
                    80947, 81001, 81031, 81045, 81056, 81073, 81083, 81145, 81168, 81177, 81207, 81208, 81226, 81245, 81269, 81285,
                    81289, 81292, 81298, 81310, 81346, 81348, 81355, 81388, 81396, 81415, 81460, 81482, 81518, 81523, 81529, 81545,
                    81570, 81576, 81604, 81613, 81631, 81656, 81679, 81684, 81698, 81736, 81747, 81765, 81775, 81805, 81842, 81859,
                    81865, 81866, 81892, 81902, 81933, 81934, 81942, 81969, 81981, 81999, 82004, 82008, 82032, 82035, 82060, 82063,
                    82071, 82081, 82082, 82105, 82106, 82120, 82125, 82156, 82176, 82203, 82210, 82241, 82287, 82323, 82330, 82366,
                    82378, 82395, 82398, 82401, 82404, 82419, 82421, 82428, 82442, 82452, 82455, 82466, 82510, 82524, 82545, 82555,
                    82581, 82588, 82591, 82636, 82658, 82675, 82678, 82716, 82725, 82743, 82744, 82762, 82770, 82809, 82846, 82855,
                    82882, 82899, 82905, 82950, 82953, 82967, 82971, 82987, 83007, 83009, 83012, 83030, 83045, 83050, 83052, 83070,
                    83076, 83086, 83128, 83141, 83153, 83156, 83194, 83225, 83261, 83264, 83322, 83382, 83391, 83399, 83406, 83427,
                    83439, 83444, 83453, 83457, 83475, 83484, 83488, 83493, 83512, 83525, 83535, 83560, 83617, 83664, 83676, 83757,
                    83770, 83775, 83835, 83838, 83848, 83856, 83878, 83884, 83896, 83902, 83922, 83977, 83985, 83988, 84034, 84046,
                    84058, 84069, 84107, 84155, 84157, 84160, 84165, 84187, 84214, 84226, 84235, 84240, 84255, 84273, 84291, 84311,
                    84317, 84328, 84346, 84357, 84361, 84372, 84388, 84415, 84438, 84444, 84447, 84451, 84465, 84471, 84496, 84554,
                    84561, 84571, 84580, 84598, 84611, 84620, 84625, 84632, 84648, 84659, 84662, 84683, 84697, 84698, 84709, 84722,
                    84753, 84775, 84789, 84885, 84890, 84899, 84926, 84946, 84958, 84976, 84979, 85003, 85014, 85017, 85024, 85029,
                    85036, 85042, 85054, 85056, 85061, 85066, 85073, 85096, 85101, 85109, 85114, 85119, 85123, 85149, 85185, 85228,
                    85236, 85281, 85282, 85293, 85302, 85319, 85353, 85354, 85356, 85389, 85418, 85446, 85450, 85463, 85497, 85534,
                    85543, 85549, 85562, 85587, 85590, 85599, 85630, 85660, 85669, 85693, 85711, 85716, 85773, 85801, 85815, 85839,
                    85842, 85864, 85867, 85903, 85917, 85921, 85928, 85934, 85974, 85994, 85999, 86004, 86008, 86046, 86067, 86084,
                    86087, 86093, 86121, 86122, 86127, 86151, 86166, 86179, 86188, 86193, 86206, 86214, 86226, 86242, 86256, 86259,
                    86298, 86300, 86307, 86310, 86319, 86321, 86328, 86356, 86360, 86375, 86399, 86400, 86403, 86430, 86446, 86460,
                    86465, 86486, 86502, 86563, 86595, 86635, 86649, 86661, 86674, 86689, 86721, 86734, 86741, 86742, 86784, 86799,
                    86808, 86837, 86869, 86900, 86949, 86993, 86999, 87030, 87041, 87044, 87084, 87116, 87138, 87162, 87188, 87211,
                    87234, 87245, 87246, 87260, 87270, 87284, 87293, 87296, 87299, 87305, 87308, 87313, 87323, 87332, 87361, 87376,
                    87392, 87419, 87432, 87435, 87445, 87466, 87491, 87503, 87558, 87570, 87581, 87603, 87615, 87623, 87635, 87647,
                    87651, 87657, 87665, 87694, 87701, 87705, 87722, 87739, 87750, 87790, 87795, 87797, 87816, 87884, 87917, 87930,
                    87942, 87953, 87954, 87984, 87989, 88008, 88016, 88038, 88042, 88044, 88049, 88083, 88119, 88134, 88138, 88143,
                    88191, 88216, 88231, 88232, 88297, 88300, 88308, 88317, 88344, 88350, 88371, 88469, 88483, 88485, 88518, 88586,
                    88605, 88621, 88651, 88656, 88665, 88668, 88712, 88717, 88751, 88754, 88756, 88785, 88801, 88807, 88826, 88848,
                    88854, 88864, 88879, 88939, 88947, 88987, 88990, 88996, 89008, 89011, 89043, 89045, 89050, 89097, 89100, 89106,
                    89134, 89178, 89189, 89202, 89211, 89224, 89241, 89251, 89254, 89258, 89295, 89328, 89357, 89403, 89420, 89426,
                    89465, 89471, 89475, 89547, 89549, 89558, 89564, 89611, 89616, 89626, 89650, 89691, 89718, 89764, 89776, 89785,
                    89813, 89817, 89861, 89876, 89889, 89904, 89939, 89948, 89952, 89964, 89969, 89979, 90009, 90022, 90031, 90043,
                    90045, 90068, 90071, 90082, 90087, 90117, 90127, 90163, 90166, 90177, 90211, 90218, 90225, 90231, 90235, 90289,
                    90344, 90347, 90379, 90387, 90418, 90435, 90444, 90472, 90478, 90485, 90486, 90513, 90514, 90532, 90567, 90595,
                    90597, 90621, 90631, 90668, 90706, 90708, 90715, 90724, 90769, 90791, 90792, 90805, 90810, 90820, 90844, 90907,
                    90925, 90926, 90934, 90946, 90981, 90999, 91003, 91019, 91022, 91050, 91055, 91064, 91078, 91117, 91120, 91129,
                    91140, 91157, 91161, 91240, 91260, 91270, 91287, 91321, 91336, 91341, 91378, 91398, 91437, 91440, 91475, 91494,
                    91500, 91511, 91567, 91572, 91601, 91617, 91653, 91678, 91705, 91723, 91725, 91733, 91761, 91768, 91790, 91804,
                    91807, 91808, 91818, 91840, 91849, 91869, 91873, 91876, 91888, 91891, 91897, 91911, 91965, 91971, 91988, 91992,
                    92004, 92021, 92035, 92068, 92098, 92104, 92121, 92127, 92151, 92162, 92186, 92191, 92192, 92207, 92260, 92282,
                    92288, 92297, 92303, 92315, 92322, 92327, 92341, 92389, 92390, 92422, 92439, 92452, 92470, 92473, 92493, 92508,
                    92517, 92529, 92551, 92557, 92596, 92611, 92613, 92642, 92666, 92684, 92723, 92732, 92738, 92783, 92808, 92816,
                    92835, 92847, 92879, 92881, 92884, 92932, 92935, 92960, 92990, 93010, 93025, 93046, 93085, 93089, 93096, 93145,
                    93155, 93169, 93179, 93184, 93204, 93211, 93213, 93227, 93285, 93292, 93320, 93356, 93371, 93374, 93385, 93403,
                    93406, 93427, 93451, 93454, 93472, 93482, 93507, 93537, 93562, 93564, 93608, 93636, 93640, 93653, 93654, 93664,
                    93676, 93721, 93740, 93743, 93745, 93751, 93770, 93796, 93820, 93824, 93833, 93841, 93847, 93848, 93854, 93882,
                    93943, 93958, 93972, 93975, 94003, 94009, 94020, 94072, 94081, 94093, 94102, 94122, 94135, 94136, 94150, 94184,
                    94187, 94258, 94264, 94272, 94278, 94281, 94317, 94348, 94376, 94382, 94387, 94389, 94393, 94402, 94421, 94422,
                    94431, 94473, 94487, 94504, 94510, 94522, 94532, 94572, 94589, 94599, 94603, 94647, 94665, 94695, 94716, 94738,
                    94740, 94778, 94803, 94810, 94815, 94821, 94862, 94886, 94900, 94924, 94952, 94963, 94970, 94980, 94992, 95017,
                    95028, 95031, 95035, 95040, 95058, 95079, 95122, 95149, 95150, 95167, 95175, 95205, 95229, 95321, 95345, 95364,
                    95379, 95386, 95395, 95416, 95419, 95433, 95464, 95490, 95496, 95504, 95513, 95523, 95525, 95558, 95572, 95591,
                    95609, 95619, 95625, 95633, 95649, 95652, 95655, 95696, 95721, 95766, 95772, 95775, 95794, 95796, 95818, 95820,
                    95832, 95842, 95851, 95854, 95859, 95868, 95881, 95895, 95943, 95949, 95978, 95980, 96010, 96020, 96024, 96063,
                    96108, 96125, 96154, 96172, 96189, 96204, 96219, 96256, 96261, 96289, 96292, 96296, 96324, 96345, 96346, 96358,
                    96386, 96412, 96428, 96445, 96446, 96448, 96458, 96475, 96482, 96494, 96502, 96508, 96511, 96516, 96520, 96547,
                    96556, 96561, 96568, 96581, 96582, 96679, 96683, 96717, 96754, 96769, 96799, 96805, 96809, 96823, 96829, 96832,
                    96850, 96856, 96906, 96962, 96974, 96979, 96997, 96998, 97002, 97054, 97064, 97067, 97110, 97116, 97137, 97138,
                    97143, 97183, 97187, 97190, 97202, 97219, 97239, 97245, 97246, 97249, 97264, 97267, 97269, 97274, 97282, 97287,
                    97294, 97299, 97306, 97347, 97361, 97371, 97435, 97466, 97473, 97474, 97559, 97565, 97587, 97611, 97622, 97625,
                    97632, 97635, 97652, 97672, 97678, 97683, 97695, 97705, 97713, 97719, 97757, 97761, 97762, 97771, 97795, 97809,
                    97826, 97845, 97877, 97882, 97891, 97903, 97961, 97970, 97975, 97996, 98001, 98014, 98023, 98035, 98052, 98059,
                    98061, 98086, 98098, 98117, 98118, 98132, 98135, 98145, 98160, 98194, 98205, 98210, 98224, 98227, 98254, 98271,
                    98272, 98302, 98320, 98346, 98356, 98360, 98366, 98478, 98483, 98486, 98497, 98528, 98537, 98551, 98557, 98572,
                    98575, 98600, 98628, 98635, 98645, 98652, 98655, 98665, 98710, 98719, 98720, 98786, 98792, 98806, 98816, 98836,
                    98859, 98864, 98879, 98905, 98917, 98929, 98969, 98975, 98976, 98981, 98999, 99020, 99026, 99054, 99083, 99093,
                    99103, 99131, 99156, 99159, 99165, 99170, 99189, 99223, 99227, 99271, 99277, 99278, 99313, 99314, 99345, 99361,
                    99367, 99386, 99394, 99417, 99418, 99453, 99454, 99477, 99518, 99544, 99559, 99592, 99597, 99603, 99621, 99646,
                    99693, 99722, 99760, 99780, 99789, 99804, 99823, 99826, 99832, 99842, 99851, 99862, 99871, 99899, 99940, 99983,
                    99985, 100025, 100043, 100070, 100076, 100084, 100096, 100105, 100108, 100123, 100132, 100141, 100181, 100191,
                    100202, 100216, 100246, 100256, 100271, 100285, 100294, 100305, 100321, 100331, 100334, 100345, 100346, 100369,
                    100372, 100382, 100403, 100451, 100488, 100499, 100501, 100511, 100536, 100629, 100652, 100655, 100669, 100687,
                    100692, 100696, 100701, 100706, 100711, 100726, 100754, 100759, 100790, 100838, 100878, 100880, 100885, 100886,
                    100943, 100948, 100957, 100962, 100968, 100981, 100986, 101001, 101012, 101015, 101019, 101037, 101063, 101105,
                    101144, 101147, 101154, 101163, 101166, 101173, 101174, 101219, 101240, 101250, 101259, 101273, 101274, 101285,
                    101300, 101309, 101310, 101321, 101324, 101363, 101366, 101377, 101380, 101389, 101407, 101411, 101432, 101476,
                    101488, 101494, 101514, 101534, 101537, 101543, 101547, 101584, 101589, 101590, 101629, 101678, 101683, 101700,
                    101712, 101748, 101757, 101762, 101771, 101785, 101795, 101822, 101851, 101863, 101894, 101903, 101924, 101939,
                    101980, 101993, 101994, 101996, 102007, 102044, 102086, 102097, 102113, 102123, 102155, 102157, 102169, 102181,
                    102182, 102185, 102199, 102208, 102213, 102226, 102290, 102296, 102306, 102317, 102337, 102344, 102350, 102378,
                    102383, 102391, 102400, 102409, 102427, 102434, 102453, 102472, 102502, 102525, 102547, 102554, 102560, 102578,
                    102580, 102597, 102616, 102625, 102632, 102663, 102669, 102681, 102694, 102706, 102715, 102723, 102730, 102743,
                    102856, 102870, 102898, 102926, 102931, 102938, 102947, 102968, 102976, 102993, 103005, 103019, 103022, 103057,
                    103080, 103100, 103106, 103126, 103173, 103202, 103207, 103219, 103226, 103228, 103248, 103274, 103288, 103291,
                    103293, 103324, 103358, 103372, 103375, 103390, 103414, 103432, 103446, 103455, 103471, 103474, 103476, 103515,
                    103527, 103542, 103562, 103569, 103572, 103581, 103609, 103612, 103618, 103637, 103644, 103653, 103663, 103671,
                    103678, 103695, 103698, 103720, 103723, 103738, 103743, 103745, 103757, 103810, 103822, 103855, 103887, 103905,
                    103915, 103925, 103929, 103945, 103954, 103969, 104011, 104055, 104111, 104116, 104125, 104145, 104162, 104176,
                    104193, 104199, 104224, 104236, 104247, 104254, 104289, 104325, 104332, 104335, 104353, 104363, 104371, 104373,
                    104406, 104412, 104440, 104459, 104461, 104469, 104479, 104483, 104490, 104500, 104536, 104560, 104569, 104588,
                    104591, 104621, 104661, 104675, 104684, 104713, 104747, 104750, 104757, 104775, 104782, 104803, 104820, 104824,
                    104829, 104830, 104836, 104851, 104882, 104920, 104941, 104963, 104989, 104999, 105006, 105040, 105066, 105089,
                    105120, 105125, 105137, 105179, 105215, 105244, 105258, 105263, 105280, 105286, 105380, 105397, 105409, 105427,
                    105452, 105469, 105472, 105477, 105490, 105505, 105529, 105530, 105604, 105614, 105622, 105625, 105652, 105687,
                    105693, 105707, 105721, 105730, 105736, 105753, 105759, 105760, 105802, 105822, 105832, 105865, 105873, 105874,
                    105880, 105899, 105902, 105909, 105919, 105922, 105946, 105975, 105992, 106005, 106057, 106081, 106093, 106117,
                    106127, 106135, 106141, 106189, 106190, 106197, 106214, 106237, 106245, 106257, 106300, 106335, 106351, 106370,
                    106375, 106376, 106384, 106403, 106405, 106406, 106429, 106447, 106466, 106495, 106532, 106542, 106547, 106564,
                    106576, 106586, 106592, 106597, 106610, 106612, 106635, 106640, 106673, 106691, 106724, 106731, 106741, 106748,
                    106765, 106789, 106822, 106856, 106861, 106870, 106883, 106895, 106928, 106938, 106979, 106985, 107005, 107012,
                    107019, 107045, 107052, 107082, 107089, 107118, 107163, 107202, 107238, 107242, 107244, 107264, 107273, 107291,
                    107293, 107312, 107318, 107335, 107349, 107350, 107363, 107390, 107405, 107408, 107427, 107433, 107444, 107514,
                    107522, 107545, 107582, 107590, 107593, 107599, 107604, 107608, 107627, 107638, 107666, 107678, 107701, 107720,
                    107723, 107725, 107731, 107737, 107767, 107779, 107788, 107799, 107819, 107833, 107881, 107901, 107911, 107930,
                    107936, 107980, 108004, 108008, 108016, 108066, 108071, 108107, 108110, 108124, 108145, 108148, 108158, 108164,
                    108197, 108198, 108219, 108222, 108224, 108227, 108290, 108302, 108329, 108335, 108338, 108362, 108398, 108405,
                    108409, 108431, 108449, 108474, 108488, 108530, 108545, 108551, 108558, 108585, 108613, 108638, 108642, 108653,
                    108671, 108675, 108684, 108687, 108696, 108786, 108792, 108795, 108818, 108830, 108836, 108839, 108851, 108875,
                    108883, 108895, 108908, 108953, 108956, 108963, 108989, 109001, 109009, 109052, 109083, 109090, 109104, 109121,
                    109128, 109141, 109146, 109155, 109162, 109209, 109216, 109243, 109265, 109282, 109306, 109316, 109334, 109359,
                    109373, 109386, 109394, 109403, 109409, 109412, 109427, 109439, 109464, 109467, 109476, 109488, 109497, 109523,
                    109551, 109556, 109566, 109598, 109628, 109643, 109663, 109674, 109688, 109691, 109704, 109715, 109717, 109746,
                    109789, 109794, 109868, 109886, 109888, 109903, 109908, 109912, 109927, 109936, 109979, 110005, 110010, 110017,
                    110041, 110042, 110047, 110048, 110053, 110054, 110057, 110091, 110093, 110112, 110127, 110178, 110183, 110184,
                    110195, 110307, 110334, 110336, 110354, 110356, 110390, 110419, 110452, 110461, 110465, 110466, 110468, 110486,
                    110511, 110531, 110561, 110573, 110593, 110611, 110614, 110651, 110661, 110665, 110701, 110730, 110735, 110738,
                    110759, 110763, 110768, 110774, 110783, 110785, 110809, 110812, 110840, 110853, 110905, 110933, 110961, 110962,
                    110974, 110987, 110989, 111007, 111011, 111043, 111070, 111088, 111127, 111140, 111149, 111229, 111230, 111243,
                    111267, 111287, 111313, 111356, 111368, 111409, 111419, 111430, 111433, 111442, 111467, 111491, 111503, 111527,
                    111531, 111541, 111546, 111548, 111577, 111625, 111636, 111650, 111655, 111662, 111673, 111699, 111727, 111729,
                    111741, 111757, 111781, 111791, 111826, 111838, 111854, 111861, 111866, 111868, 111897, 111903, 111904, 111959,
                    111975, 112018, 112023, 112033, 112036, 112043, 112095, 112113, 112136, 112153, 112165, 112166, 112177, 112189,
                    112226, 112237, 112240, 112255, 112265, 112280, 112292, 112307, 112316, 112342, 112345, 112351, 112361, 112364,
                    112372, 112382, 112389, 112408, 112417, 112423, 112476, 112480, 112483, 112513, 112538, 112543, 112574, 112599,
                    112605, 112667, 112688, 112735, 112746, 112810, 112817, 112827, 112835, 112892, 112898, 112915, 112918, 112937,
                    112940, 112958, 113013, 113014, 113027, 113048, 113051, 113053, 113102, 113104, 113114, 113130, 113159, 113180,
                    113189, 113211, 113245, 113261, 113273, 113292, 113314, 113319, 113323, 113326, 113337, 113338, 113355, 113365,
                    113376, 113408, 113462, 113476, 113503, 113550, 113578, 113580, 113634, 113653, 113677, 113713, 113751, 113771,
                    113781, 113797, 113846, 113849, 113855, 113878, 113884, 113926, 113938, 113956, 113980, 113986, 113991, 114005,
                    114022, 114036, 114046, 114050, 114061, 114067, 114117, 114142, 114158, 114165, 114200, 114219, 114254, 114265,
                    114272, 114312, 114318, 114348, 114377, 114383, 114386, 114431, 114448, 114479, 114487, 114513, 114542, 114572,
                    114584, 114600, 114605, 114623, 114628, 114640, 114650, 114668, 114671, 114674, 114700, 114731, 114748, 114759,
                    114766, 114768, 114784, 114808, 114813, 114829, 114830, 114842, 114875, 114898, 114903, 114913, 114928, 114937,
                    114958, 114963, 115000, 115018, 115023, 115028, 115042, 115084, 115096, 115105, 115117, 115149, 115155, 115198,
                    115221, 115222, 115225, 115276, 115294, 115297, 115321, 115348, 115364, 115376, 115385, 115386, 115400, 115414,
                    115465, 115485, 115492, 115501, 115519, 115527, 115531, 115567, 115572, 115631, 115648, 115660, 115672, 115681,
                    115688, 115694, 115699, 115719, 115726, 115733, 115796, 115879, 115880, 115888, 115908, 115925, 115942, 115978,
                    115986, 116014, 116019, 116031, 116048, 116069, 116091, 116103, 116107, 116145, 116152, 116247, 116289, 116316,
                    116344, 116354, 116368, 116377, 116383, 116387, 116408, 116428, 116445, 116476, 116482, 116488, 116493, 116502,
                    116518, 116524, 116527, 116549, 116561, 116562, 116568, 116577, 116590, 116602, 116607, 116611, 116626, 116637,
                    116647, 116665, 116674, 116700, 116714, 116719, 116744, 116764, 116774, 116778, 116798, 116803, 116806, 116827,
                    116839, 116863, 116873, 116882, 116887, 116915, 116939, 116944, 116953, 116965, 117002, 117007, 117015, 117032,
                    117035, 117052, 117087, 117139, 117157, 117193, 117211, 117214, 117220, 117238, 117248, 117275, 117293, 117301,
                    117319, 117338, 117347, 117361, 117380, 117389, 117398, 117435, 117458, 117476, 117498, 117500, 117529, 117554,
                    117556, 117565, 117580, 117585, 117592, 117597, 117616, 117637, 117644, 117659, 117703, 117704, 117727, 117738,
                    117770, 117796, 117799, 117820, 117832, 117840, 117843, 117865, 117868, 117874, 117886, 117962, 117967, 117995,
                    118000, 118017, 118037, 118038, 118051, 118057, 118072, 118080, 118098, 118107, 118109, 118114, 118123, 118150,
                    118159, 118164, 118173, 118201, 118212, 118246, 118257, 118303, 118310, 118313, 118324, 118334, 118336, 118339,
                    118353, 118354, 118390, 118396, 118436, 118453, 118466, 118492, 118506, 118525, 118557, 118561, 118586, 118606,
                    118627, 118641, 118654, 118687, 118688, 118735, 118737, 118750, 118773, 118778, 118786, 118809, 118833, 118853,
                    118854, 118863, 118881, 118896, 118917, 118921, 118939, 118948, 118960, 118970, 118983, 119025, 119026, 119064,
                    119069, 119079, 119085, 119115, 119165, 119166, 119193, 119194, 119199, 119215, 119218, 119241, 119277, 119301,
                    119306, 119335, 119344, 119364, 119373, 119382, 119410, 119425, 119459, 119473, 119486, 119500, 119508, 119511,
                    119517, 119528, 119533, 119539, 119542, 119546, 119566, 119587, 119590, 119607, 119614, 119625, 119633, 119634,
                    119643, 119683, 119704, 119710, 119734, 119752, 119760, 119826, 119922, 119931, 119977, 120015, 120020, 120083,
                    120086, 120101, 120105, 120155, 120188, 120195, 120216, 120228, 120238, 120255, 120317, 120327, 120369, 120382,
                    120404, 120414, 120432, 120435, 120454, 120463, 120471, 120505, 120528, 120588, 120616, 120630, 120644, 120672,
                    120677, 120718, 120725, 120730, 120756, 120759, 120765, 120795, 120821, 120850, 120868, 120880, 120885, 120892,
                    120897, 120912, 120934, 120991, 121004, 121021, 121022, 121036, 121041, 121069, 121078, 121126, 121135, 121138,
                    121162, 121176, 121186, 121209, 121212, 121221, 121225, 121228, 121239, 121245, 121264, 121270, 121299, 121306,
                    121315, 121317, 121321, 121324, 121330, 121339, 121346, 121370, 121372, 121393, 121396, 121414, 121442, 121475,
                    121499, 121512, 121518, 121588, 121597, 121612, 121629, 121630, 121634, 121680, 121725, 121798, 121815, 121822,
                    121837, 121840, 121869, 121878, 121897, 121920, 121929, 121944, 121999, 122008, 122056, 122062, 122067, 122083,
                    122095, 122110, 122118, 122127, 122189, 122207, 122226, 122253, 122301, 122324, 122337, 122362, 122368, 122371,
                    122378, 122404, 122443, 122451, 122457, 122458, 122479, 122484, 122521, 122540, 122545, 122552, 122560, 122575,
                    122578, 122605, 122661, 122673, 122722, 122728, 122739, 122751, 122762, 122769, 122772, 122782, 122798, 122810,
                    122812, 122818, 122829, 122851, 122857, 122868, 122893, 122894, 122902, 122921, 122959, 122984, 123001, 123038,
                    123076, 123100, 123107, 123122, 123139, 123175, 123190, 123201, 123219, 123232, 123255, 123285, 123328, 123385,
                    123397, 123404, 123419, 123435, 123437, 123443, 123500, 123506, 123511, 123512, 123518, 123522, 123545, 123572,
                    123587, 123594, 123607, 123608, 123630, 123635, 123659, 123692, 123695, 123700, 123730, 123748, 123751, 123770,
                    123793, 123800, 123803, 123851, 123859, 123866, 123868, 123877, 123884, 123909, 123910, 123919, 123927, 123937,
                    123950, 123957, 123979, 123996, 124040, 124054, 124063, 124067, 124088, 124101, 124154, 124162, 124167, 124176,
                    124195, 124230, 124267, 124346, 124389, 124418, 124420, 124454, 124490, 124503, 124519, 124533, 124544, 124589,
                    124616, 124630, 124636, 124645, 124652, 124663, 124667, 124690, 124755, 124757, 124819, 124862, 124864, 124874,
                    124882, 124903, 124915, 124938, 124945, 124973, 124986, 124991, 125011, 125014, 125082, 125105, 125163, 125165,
                    125205, 125219, 125239, 125240, 125257, 125284, 125302, 125327, 125336, 125355, 125363, 125365, 125372, 125378,
                    125383, 125387, 125390, 125414, 125435, 125466, 125481, 125522, 125531, 125544, 125564, 125588, 125602, 125613,
                    125621, 125648, 125651, 125681, 125719, 125773, 125774, 125779, 125786, 125792, 125822, 125828, 125831, 125837,
                    125880, 125891, 125922, 125928, 125931, 125941, 125948, 125960, 125973, 126014, 126016, 126036, 126043, 126062,
                    126076, 126116, 126125, 126145, 126152, 126175, 126188, 126199, 126205, 126213, 126220, 126286, 126294, 126300,
                    126322, 126324, 126343, 126344, 126430, 126433, 126448, 126451, 126457, 126458, 126463, 126470, 126484, 126518,
                    126521, 126547, 126556, 126583, 126584, 126633, 126673, 126680, 126686, 126689, 126702, 126710, 126722, 126787,
                    126789, 126794, 126801, 126857, 126891, 126896, 126902, 126911, 126919, 126943, 126947, 126949, 126953, 126971,
                    126976, 126982, 127009, 127021, 127027, 127039, 127041, 127044, 127059, 127071, 127075, 127126, 127129, 127145,
                    127154, 127165, 127171, 127197, 127198, 127207, 127226, 127240, 127254, 127269, 127305, 127323, 127342, 127347,
                    127363, 127394, 127426, 127432, 127440, 127462, 127468, 127473, 127486, 127502, 127510, 127523, 127543, 127567,
                    127570, 127585, 127588, 127619, 127621, 127626, 127628, 127640, 127662, 127684, 127687, 127693, 127696, 127708,
                    127711, 127722, 127732, 127747, 127767, 127771, 127790, 127816, 127824, 127833, 127869, 127910, 127928, 127933,
                    127963, 127975, 128016, 128031, 128059, 128074, 128107, 128122, 128157, 128161, 128200, 128213, 128229, 128233,
                    128241, 128259, 128295, 128299, 128313, 128345, 128362, 128375, 128376, 128406, 128410, 128416, 128443, 128451,
                    128465, 128466, 128484, 128499, 128502, 128524, 128536, 128541, 128542, 128592, 128598, 128623, 128632, 128671,
                    128713, 128724, 128731, 128761, 128767, 128782, 128793, 128805, 128820, 128830, 128842, 128852, 128859, 128866,
                    128875, 128892, 128896, 128914, 128935, 128964, 128973, 129001, 129007, 129012, 129021, 129026, 129055, 129071,
                    129093, 129105, 129124, 129148, 129151, 129155, 129161, 129200, 129224, 129230, 129237, 129298, 129300, 129316,
                    129345, 129351, 129352, 129363, 129393, 129410, 129415, 129449, 129477, 129501, 129518, 129520, 129545, 129563,
                    129594, 129608, 129616, 129652, 129659, 129680, 129692, 129738, 129764, 129782, 129793, 129820, 129824, 129829,
                    129848, 129854, 129861, 129879, 129886, 129907, 129949, 129953, 129980, 129998, 130031, 130043, 130057, 130065,
                    130087, 130091, 130096, 130101, 130111, 130126, 130137, 130138, 130149, 130177, 130180, 130214, 130228, 130240,
                    130267, 130280, 130294, 130306, 130325, 130398, 130402, 130421, 130438, 130441, 130475, 130486, 130503, 130551,
                    130574, 130602, 130612, 130621, 130633, 130684, 130688, 130698, 130700, 130712, 130739, 130748, 130774, 130783,
                    130802, 130811, 130836, 130840, 130856, 130864, 130873, 130918, 130927, 130929, 130958, 130966, 130970, 130976,
                    131006, 131008, 131020, 131059, -1 };
            primitivePolynomials[17] = PrimitivePolynomialDegree18;
        }
    }

    private void applyPrimitivePolynomialDegree18() throws ArithmeticException {
        if (ppmtMaxDim > N_PRIMITIVES_UP_TO_DEGREE_18)
            throw new ArithmeticException("too many polynomials requested. not provided by this file");
    }

}

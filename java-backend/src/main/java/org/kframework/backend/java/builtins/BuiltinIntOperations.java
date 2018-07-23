// Copyright (c) 2013-2018 K Team. All Rights Reserved.
package org.kframework.backend.java.builtins;

import org.kframework.backend.java.kil.Term;
import org.kframework.backend.java.kil.TermContext;

import java.math.BigInteger;
import java.util.Random;

/**
 * Table of {@code public static} methods on builtin integers.
 *
 * @author: AndreiS
 */
public class BuiltinIntOperations {

    public static Term add(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return IntToken.of(term1.bigIntegerValue().add(term2.bigIntegerValue()));
    }

    public static Term sub(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return IntToken.of(term1.bigIntegerValue().subtract(term2.bigIntegerValue()));
    }

    public static Term mul(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return IntToken.of(term1.bigIntegerValue().multiply(term2.bigIntegerValue()));
    }

    public static Term div(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        try {
            return IntToken.of(term1.bigIntegerValue().divide(term2.bigIntegerValue()));
        } catch (ArithmeticException e) {
            return null;
        }
    }

    public static Term ediv(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        try {
            return IntToken.of((term1.bigIntegerValue().signum() < 0 ?
                    (term1.bigIntegerValue().add(BigInteger.ONE).subtract(term2.bigIntegerValue())) : term1.bigIntegerValue())
                    .divide(term2.bigIntegerValue()));
        } catch (ArithmeticException e) {
            return null;
        }
    }

    public static Term rem(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        try {
            return IntToken.of(term1.bigIntegerValue().remainder(term2.bigIntegerValue()));
        } catch (ArithmeticException e) {
            return null;
        }
    }

    public static Term mod(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return IntToken.of(term1.bigIntegerValue().mod(term2.bigIntegerValue()));
    }

    public static Term pow(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return IntToken.of(term1.bigIntegerValue().pow(term2.bigIntegerValue().intValueExact()));
    }

    public static Term powmod(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        IntToken term3 = (IntToken) terms[2];
        return IntToken.of(term1.bigIntegerValue().modPow(term2.bigIntegerValue(), term3.bigIntegerValue()));
    }

    public static Term shl(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return IntToken.of(term1.bigIntegerValue().shiftLeft(term2.bigIntegerValue().intValueExact()));
    }

    public static Term shr(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        try {
            return IntToken.of(term1.bigIntegerValue().shiftRight(term2.bigIntegerValue().intValueExact()));
        } catch (ArithmeticException e) {
           if (term1.bigIntegerValue().compareTo(BigInteger.ZERO) >= 0) {
               return IntToken.of(0);
           } else {
               return IntToken.of(-1);
           }
        }
    }

    public static Term not(Term[] terms, TermContext context) {
        IntToken term = (IntToken) terms[0];
        return IntToken.of(term.bigIntegerValue().not());
    }

    public static Term and(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return IntToken.of(term1.bigIntegerValue().and(term2.bigIntegerValue()));
    }

    public static Term or(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return IntToken.of(term1.bigIntegerValue().or(term2.bigIntegerValue()));
    }

    public static Term xor(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return IntToken.of(term1.bigIntegerValue().xor(term2.bigIntegerValue()));
    }

    public static Term min(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return IntToken.of(term1.bigIntegerValue().min(term2.bigIntegerValue()));
    }

    public static Term max(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return IntToken.of(term1.bigIntegerValue().max(term2.bigIntegerValue()));
    }

    public static Term abs(Term[] terms, TermContext context) {
        IntToken term = (IntToken) terms[0];
        return IntToken.of(term.bigIntegerValue().abs());
    }

    public static Term log2(Term[] terms, TermContext context) {
        IntToken term = (IntToken) terms[0];
        BigInteger val = term.bigIntegerValue();
        if (val.compareTo(BigInteger.ZERO) <= 0)
            return null;
        int log2 = 0;
        while (val.compareTo(BigInteger.ONE) > 0) {
            val = val.shiftRight(1);
            log2++;
        }
        return IntToken.of(log2);
    }

    public static Term eq(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return BoolToken.of(term1.bigIntegerValue().compareTo(term2.bigIntegerValue()) == 0);
    }

    public static Term ne(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return BoolToken.of(term1.bigIntegerValue().compareTo(term2.bigIntegerValue()) != 0);
    }

    public static Term gt(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return BoolToken.of(term1.bigIntegerValue().compareTo(term2.bigIntegerValue()) > 0);
    }

    public static Term ge(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return BoolToken.of(term1.bigIntegerValue().compareTo(term2.bigIntegerValue()) >= 0);
    }

    public static Term lt(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return BoolToken.of(term1.bigIntegerValue().compareTo(term2.bigIntegerValue()) < 0);
    }

    public static Term le(Term[] terms, TermContext context) {
        IntToken term1 = (IntToken) terms[0];
        IntToken term2 = (IntToken) terms[1];
        return BoolToken.of(term1.bigIntegerValue().compareTo(term2.bigIntegerValue()) <= 0);
    }

    private static final Random randomGenerator = new Random();

    public static Term rand(Term[] terms, TermContext context) {
        IntToken upperBound = (IntToken) terms[0];
        if (upperBound.bigIntegerValue().compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
            return null;
        }
        return IntToken.of(randomGenerator.nextInt(upperBound.intValue()));
    }

}

package nl.hu.cisq1.lingo.words.domain;

import java.util.Optional;

public enum Letter {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I,
    J,
    K,
    L,
    M,
    N,
    O,
    P,
    Q,
    R,
    S,
    T,
    U,
    V,
    W,
    X,
    Y,
    Z,
    DOT;

    public static Optional<Letter> charToLetter(char ch) {
        switch (ch) {
            case 'A': return Optional.of(Letter.A);
            case 'B': return Optional.of(Letter.B);
            case 'C': return Optional.of(Letter.C);
            case 'D': return Optional.of(Letter.D);
            case 'E': return Optional.of(Letter.E);
            case 'F': return Optional.of(Letter.F);
            case 'G': return Optional.of(Letter.G);
            case 'H': return Optional.of(Letter.H);
            case 'I': return Optional.of(Letter.I);
            case 'J': return Optional.of(Letter.J);
            case 'K': return Optional.of(Letter.K);
            case 'L': return Optional.of(Letter.L);
            case 'M': return Optional.of(Letter.M);
            case 'N': return Optional.of(Letter.N);
            case 'O': return Optional.of(Letter.O);
            case 'P': return Optional.of(Letter.P);
            case 'Q': return Optional.of(Letter.Q);
            case 'R': return Optional.of(Letter.R);
            case 'S': return Optional.of(Letter.S);
            case 'T': return Optional.of(Letter.T);
            case 'U': return Optional.of(Letter.U);
            case 'V': return Optional.of(Letter.V);
            case 'W': return Optional.of(Letter.W);
            case 'X': return Optional.of(Letter.X);
            case 'Y': return Optional.of(Letter.Y);
            case 'Z': return Optional.of(Letter.Z);
            case '.': return Optional.of(Letter.DOT);
        }
        return Optional.empty();
    }
}
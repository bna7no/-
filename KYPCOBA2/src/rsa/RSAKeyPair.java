package rsa;

import java.math.BigInteger;

public class RSAKeyPair {

    private final BigInteger publicExponent;
    private final BigInteger privateExponent;
    private final BigInteger modulus;

    public RSAKeyPair(
            BigInteger publicExponent,
            BigInteger privateExponent,
            BigInteger modulus
    ) {
        this.publicExponent = publicExponent;
        this.privateExponent = privateExponent;
        this.modulus = modulus;
    }

    public BigInteger getPublicExponent() {
        return publicExponent;
    }

    public BigInteger getPrivateExponent() {
        return privateExponent;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public int getKeySize() {
        return modulus.bitLength();
    }
}
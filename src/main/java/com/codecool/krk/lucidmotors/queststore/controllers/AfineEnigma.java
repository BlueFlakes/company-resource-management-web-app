package com.codecool.krk.lucidmotors.queststore.controllers;

import java.util.Arrays;

public class AfineEnigma {

    public static void main(String[] args) {
        System.out.println(AfineEnigma.getAfineEnigma().encipher("tepes"));
    }

    private static AfineEnigma afineEnigma = null;

    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private Integer key1 = 7;
    private Integer key2 = 11;

    public static AfineEnigma getAfineEnigma() {
        if (afineEnigma == null) {
            synchronized (AfineEnigma.class) {
                if (afineEnigma == null) {
                    afineEnigma = new AfineEnigma();
                }
            }
        }
        return afineEnigma;
    }

    private AfineEnigma() {

    }

    public String encipher(String text)
    {
        String encipheredText = "";

        for(Character c : text.toCharArray()){
            String alphabet = getAlphabetCased(c);
            if (alphabet.indexOf(c) >= 0){
                Integer charValue = alphabet.indexOf(c);
                charValue = ((this.key1 * charValue) + this.key2) % alphabet.length();
                encipheredText += alphabet.charAt(charValue);
            }
            else {
                encipheredText += c;
            }
        }
        return encipheredText;
    }

    public String getAlphabetCased(Character chr){
        String alphabet;
        if(Character.isUpperCase(chr)) alphabet = this.alphabet.toUpperCase();
        else alphabet = this.alphabet;

        return alphabet;
    }

    public String decipher(String text)
    {
        String decipheredText = "";
        Integer multiplicativeInverse = findMultiplicativeInverse();
        for(Character c : text.toCharArray()){
            String alphabet = getAlphabetCased(c);
            if (alphabet.indexOf(c) >= 0){
                Integer charValue = alphabet.indexOf(c);
                charValue -= this.key2;
                if(charValue < 0) charValue = alphabet.length() + charValue;
                charValue = multiplicativeInverse * charValue;
                charValue %= alphabet.length();
                decipheredText += alphabet.charAt(charValue);
            }
            else{
                decipheredText += c;
            }
        }
        return decipheredText;
    }

    private Integer findMultiplicativeInverse(){
        Integer x = 1;
        while((this.key1 * x) % alphabet.length() != 1){
            x++;
        }
        return x;
    }


}
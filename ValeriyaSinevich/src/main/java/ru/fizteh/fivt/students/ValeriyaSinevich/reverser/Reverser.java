package ru.fizteh.fivt.students.ValeriyaSinevich.reverser;

/**
 * Created by root on 9/26/15.
 */
public final class Reverser {

    public static void main(final String[] args) {
        for (int i = args.length - 1; i >= 0; --i) {
            String[] words = args[i].split("//s+");
            for (int j = words.length - 1; j >= 0; --j){
                System.out.print(words[i] + " ");
            }
        }
        System.out.println();
    }

}

package nl.michelbijnen.tool.substitution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryKeeper {
    private List<char[]> arrays = new ArrayList<>();

    public boolean checkArray(char[] arr) {
        for (char[] arr1 : this.arrays) {
            if (Arrays.equals(arr, arr1)) {
                return false;
            }
        }

        char[] temp = new char[arr.length];
        System.arraycopy(arr, 0, temp, 0, arr.length);
        this.arrays.add(temp);
        return true;
    }
}

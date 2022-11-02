package org.example;

public class CalculationCategory {


    public static String[] setMaxCategory(String category, int sum) {

        String sumMaxCategory = null;
        String nameMaxCategory = null;
        String[] maxCategory = new String[0];

        int[] sumCategory = {0, 0, 0, 0, 0};
        String[] nameCategory = {"еда", "одежда", "быт", "финансы", "другое"};

        for (int i = 0; i < sumCategory.length; i++) {
            if (category.equals(nameCategory[i])) {
                sumCategory[i] += sum;
                sumMaxCategory = Integer.toString(sumCategory[i]);
                nameMaxCategory = nameCategory[i];


                maxCategory = new String[]{nameMaxCategory, sumMaxCategory};

            }

        }
        return maxCategory;
    }

}

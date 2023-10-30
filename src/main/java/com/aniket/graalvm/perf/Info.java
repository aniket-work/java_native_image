package com.aniket.graalvm.perf;

public class Info {

    public static void main(String[] args) {
        Table table = new Table();
        table.addHeader("Benchmark Method", "Description", "Parameters", "Iterations", "Warmup Iterations", "Warmup Time", "Measurement Time");
        table.addRow("performMemoryIntensiveOperation", "Memory-Intensive Operation", "", "1", "1", "50 seconds", "50 seconds");
        table.addRow("measureSuperIntenseCPUMemoryOperation", "Super Intense CPU & Memory Operation", "", "1", "1", "50 seconds", "50 seconds");
        table.addRow("startupTimeComparison", "Startup Time Comparison", "", "1", "1", "50 seconds", "50 seconds");
        table.print();
    }

    private static class Table {
        private String[][] data;
        private int[] columnWidths;

        public Table() {
            data = new String[0][0];
            columnWidths = new int[0];
        }

        public void addHeader(String... columns) {
            data = new String[1][columns.length];
            columnWidths = new int[columns.length];
            for (int i = 0; i < columns.length; i++) {
                data[0][i] = columns[i];
                columnWidths[i] = columns[i].length();
            }
        }

        public void addRow(String... rowData) {
            if (rowData.length != data[0].length) {
                throw new IllegalArgumentException("Row data length does not match header length.");
            }
            String[][] newData = new String[data.length + 1][data[0].length];
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    newData[i][j] = data[i][j];
                    if (rowData[j].length() > columnWidths[j]) {
                        columnWidths[j] = rowData[j].length();
                    }
                }
            }
            for (int j = 0; j < data[0].length; j++) {
                newData[data.length][j] = rowData[j];
                if (rowData[j].length() > columnWidths[j]) {
                    columnWidths[j] = rowData[j].length();
                }
            }
            data = newData;
        }

        public void print() {
            System.out.println("+" + getDashes(columnWidths) + "+");
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    System.out.print("| ");
                    System.out.print(data[i][j]);
                    System.out.print(getSpaces(columnWidths[j] - data[i][j].length()) + " ");
                }
                System.out.println("|");
                if (i == 0) {
                    System.out.println("+" + getDashes(columnWidths) + "+");
                }
            }
            System.out.println("+" + getDashes(columnWidths) + "+");
        }

        private String getSpaces(int count) {
            return " ".repeat(count);
        }

        private String getDashes(int[] columnWidths) {
            StringBuilder result = new StringBuilder();
            for (int columnWidth : columnWidths) {
                result.append("+");
                result.append(getDashes(columnWidth + 2));
            }
            result.append("+");
            return result.toString();
        }

        private String getDashes(int count) {
            return "-".repeat(count);
        }
    }
}

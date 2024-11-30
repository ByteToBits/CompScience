


import java.util.ArrayList;
import java.util.List;

public class NowhereLandPrinter {
    public static void main(String[] args) {
        // Sample input data from assignment
        int playerBuildingLocation = 1;
        int fuelReserves = 10;
        int portalBuildingIndex = 4;
        int frozenBuildingIndex = 12;
        int webTrapBuildingIndex = 10;
        int[] fuelCellBuildings = {2, 5, 9, 13};
        int[] buildingHeights = {3, 5, 5, 2, 4, 1, 3, 5, 4, 4, 2, 4, 2, 4, 3};

        List<String[]> map = new ArrayList<>();

        // Create levels for each building height
        for (int level = 5; level >= 1; level--) {
            String[] row = new String[15];
            for (int i = 0; i < 15; i++) {
                if (buildingHeights[i] >= level) {
                    if (playerBuildingLocation - 1 == i) {
                        row[i] = "<P1>";
                    } else if (portalBuildingIndex - 1 == i && level == buildingHeights[i]) {
                        row[i] = "-{}-";
                    } else if (frozenBuildingIndex - 1 == i && level == buildingHeights[i]) {
                        row[i] = "[FF]";
                    } else if (webTrapBuildingIndex - 1 == i && level == buildingHeights[i]) {
                        row[i] = "[##]";
                    } else if (contains(fuelCellBuildings, i + 1) && level == buildingHeights[i]) {
                        row[i] = "[FC]";
                    } else {
                        row[i] = "[  ]";
                    }
                } else {
                    row[i] = "    ";
                }
            }
            map.add(row);
        }

        // Printing the map
        for (int level = 5; level >= 1; level--) {
            System.out.print("|  Level " + level + ":  ");
            String[] row = map.get(5 - level);
            for (String building : row) {
                System.out.print(building + "  ");
            }
            System.out.println("|");
        }

        // Print building index row
        System.out.print("| Building:  ");
        for (int i = 1; i <= 15; i++) {
            System.out.printf("%4d", i);
        }
        System.out.println("               |");

        // Print building heights row
        System.out.print("|   Height: ");
        for (int height : buildingHeights) {
            System.out.printf("%5s", "(" + height + ")");
        }
        System.out.println("          |");

        // Legends
        System.out.println("|------------------------------------------------------------------------------------------------------------|");
        System.out.println("| Legends:  <P1> Player  |  -{}- Portal  |  [FC] Fuel Cell  |  [##] Web Trap |  [FF] Frozen Building         |");
    }

    // Helper method to check if array contains a value
    private static boolean contains(int[] arr, int value) {
        for (int num : arr) {
            if (num == value) {
                return true;
            }
        }
        return false;
    }
}

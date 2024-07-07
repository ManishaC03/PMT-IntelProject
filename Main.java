import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Device {
    private String name;
    private int maxBatteryLife;
    private int powerConsumptionRate;
    private int remainingBattery;

    public Device(String name, int maxBatteryLife, int powerConsumptionRate) {
        this.name = name;
        this.maxBatteryLife = maxBatteryLife;
        this.powerConsumptionRate = powerConsumptionRate;
        this.remainingBattery = maxBatteryLife;
    }

    public void usePower(int time) {
        int powerUsed = powerConsumptionRate * time;
        if (powerUsed >= remainingBattery) {
            remainingBattery = 0;
        } else {
            remainingBattery -= powerUsed;
        }
    }

    public String getName() {
        return name;
    }

    public int getMaxBatteryLife() {
        return maxBatteryLife;
    }

    public int getPowerConsumptionRate() {
        return powerConsumptionRate;
    }

    public int getRemainingBattery() {
        return remainingBattery;
    }
}

class PowerManager {
    private List<Device> devices;

    public PowerManager() {
        this.devices = new ArrayList<>();
    }

    public void addDevice(Device device) {
        this.devices.add(device);
    }

    public void usePower(String deviceName, int time) {
        for (Device device : devices) {
            if (device.getName().equals(deviceName)) {
                device.usePower(time);
                break;
            }
        }
    }

    public List<Device> getTelemetry() {
        return new ArrayList<>(devices);
    }

    public List<String> alertLowBattery(int threshold) {
        List<String> lowBatteryDevices = new ArrayList<>();
        for (Device device : devices) {
            if (device.getRemainingBattery() <= threshold) {
                lowBatteryDevices.add(device.getName());
            }
        }
        return lowBatteryDevices;
    }

    public void compareDevices(String deviceName1, String deviceName2) {
        Device device1 = null;
        Device device2 = null;

        for (Device device : devices) {
            if (device.getName().equals(deviceName1)) {
                device1 = device;
            }
            if (device.getName().equals(deviceName2)) {
                device2 = device;
            }
        }

        if (device1 != null && device2 != null) {
            System.out.println("Comparison between " + deviceName1 + " and " + deviceName2 + ":");
            System.out.println("Attribute\t\t" + deviceName1 + "\t\t" + deviceName2);
            System.out.println("------------------------------------------------------------");
            System.out.println("Max Battery Life\t" + device1.getMaxBatteryLife() + "\t\t" + device2.getMaxBatteryLife());
            System.out.println("Power Consumption\t" + device1.getPowerConsumptionRate() + "\t\t" + device2.getPowerConsumptionRate());
            System.out.println("Remaining Battery\t" + device1.getRemainingBattery() + "\t\t" + device2.getRemainingBattery());
        } else {
            if (device1 == null) {
                System.out.println("Device " + deviceName1 + " not found.");
            }
            if (device2 == null) {
                System.out.println("Device " + deviceName2 + " not found.");
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        PowerManager powerManager = new PowerManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add device");
            System.out.println("2. Use power");
            System.out.println("3. Show telemetry data");
            System.out.println("4. Show low battery alerts");
            System.out.println("5. Compare devices");
            System.out.println("6. Exit");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter device name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter maximum battery life: ");
                    int maxBatteryLife = scanner.nextInt();
                    System.out.print("Enter power consumption rate: ");
                    int powerConsumptionRate = scanner.nextInt();
                    Device device = new Device(name, maxBatteryLife, powerConsumptionRate);
                    powerManager.addDevice(device);
                    break;

                case 2:
                    System.out.print("Enter device name: ");
                    String deviceName = scanner.nextLine();
                    System.out.print("Enter time to use power: ");
                    int time = scanner.nextInt();
                    powerManager.usePower(deviceName, time);
                    break;

                case 3:
                    System.out.println("Telemetry Data:");
                    for (Device d : powerManager.getTelemetry()) {
                        System.out.println("Device: " + d.getName() +
                                ", Remaining Battery: " + d.getRemainingBattery() +
                                ", Max Battery Life: " + d.getMaxBatteryLife() +
                                ", Power Consumption Rate: " + d.getPowerConsumptionRate());
                    }
                    break;

                case 4:
                    System.out.print("Enter battery threshold: ");
                    int threshold = scanner.nextInt();
                    System.out.println("Low Battery Alerts:");
                    for (String lowBatteryDevice : powerManager.alertLowBattery(threshold)) {
                        System.out.println("Device: " + lowBatteryDevice);
                    }
                    break;

                case 5:
                    System.out.print("Enter the first device name: ");
                    String deviceName1 = scanner.nextLine();
                    System.out.print("Enter the second device name: ");
                    String deviceName2 = scanner.nextLine();
                    powerManager.compareDevices(deviceName1, deviceName2);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}



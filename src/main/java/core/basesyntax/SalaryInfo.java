package core.basesyntax;

import core.basesyntax.exception.IllegalDateParametersException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo)
            throws Exception {

        LocalDate dateOfStart = LocalDate.parse(dateFrom, formatter);
        LocalDate dateOfEnd = LocalDate.parse(dateTo, formatter);

        if (dateOfEnd.isBefore(dateOfStart)) {
            throw new IllegalDateParametersException("Wrong parameters");
        }

        StringBuilder report = new StringBuilder();
        report.append("Отчёт за период ").append(dateFrom).append(" - ").append(dateTo);

        for (String name : names) {
            int salary = 0;

            for (String entry : data) {
                String[] dateNameHoursRate = entry.split(" ");
                if (dateNameHoursRate.length < 4) {
                    throw new IllegalArgumentException("Wrong data parameter");
                }
                LocalDate dateOfEntry = LocalDate.parse(dateNameHoursRate[0], formatter);

                if (name.equals(dateNameHoursRate[1]) && !(dateOfEntry.isAfter(dateOfEnd)
                        || dateOfEntry.isBefore(dateOfStart))) {
                    salary += Integer.parseInt(dateNameHoursRate[2])
                            * Integer.parseInt(dateNameHoursRate[3]);
                }
            }

            report.append("\n").append(name).append(" - ").append(salary);
        }

        return report.toString();
    }
}

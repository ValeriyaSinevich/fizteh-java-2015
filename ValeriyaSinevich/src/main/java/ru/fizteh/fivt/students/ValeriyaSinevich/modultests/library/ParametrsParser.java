package ru.fizteh.fivt.students.ValeriyaSinevich.modultests.library;
import java.util.List;
import java.util.ArrayList;

import com.beust.jcommander.Parameter;

class ParametersParser {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = { "--query", "-q" }, description = "substring")
    private String substring = "";

    @Parameter(names = {"--place", "-p"}, description = "places")
    private String place =  "";

    @Parameter(names = {"--hideRetweets"}, description = "hideRetweets")
    private boolean hide = false;

    public boolean isHide() {
        return hide;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public String getSubstring() {
        return substring;
    }

    public String getPlace() {
        return place;
    }

    public int getNumber() {
        return number;
    }

    @Parameter(names = {"--limit", "-l"}, description = "limit")
    private int number = Integer.MAX_VALUE;

    public boolean isStream() {
        return stream;
    }

    @Parameter(names = {"--stream", "-s"}, description = "limit")
    private boolean stream = false;

    @Parameter(names = {"--help", "-h"}, description = "help")
    private boolean help = false;

    public boolean isHelp() {
        return help;
    }
}


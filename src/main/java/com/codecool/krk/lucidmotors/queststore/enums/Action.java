package com.codecool.krk.lucidmotors.queststore.enums;

public enum Action {
    STUDENT(StudentOptions.class),
    MANAGER(ManagerOptions.class),
    MENTOR(MentorOptions.class),
    DEFAULT(DEFAULT_ACTION.class);

    private enum DEFAULT_ACTION {
        DEFAULT
    }

    private Class options;

    Action(Class<? extends Enum> attachedClass) {
        this.options = attachedClass;
    }

    public static Action getUserAction(Roles role) {
        return EnumUtils.getValue(Action.class, role.toString());
    }

    public Enum prepareCommand(Roles role, String userChoice) {
        String roleName = role.toString();
        Action action = EnumUtils.getValue(this.getDeclaringClass(), roleName);

        if (action != Action.DEFAULT) {
            return EnumUtils.getValue(action.options, userChoice);
        } else {
            return action;
        }
    }
}

package apps.cdmp.goalhelper.bindmodel.addgoal

import apps.cdmp.goalhelper.bindmodel.FormField

data class AddGoal(var name: FormField<String>, var validated: Boolean, var deadline: Boolean = true)
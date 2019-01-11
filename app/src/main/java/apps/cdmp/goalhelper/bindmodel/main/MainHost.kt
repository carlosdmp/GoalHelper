package apps.cdmp.goalhelper.bindmodel.main

data class MainHost(val logo: MainButtonLogo = MainButtonLogo.HIDDEN)

enum class MainButtonLogo { HIDDEN, ADD, DONE }
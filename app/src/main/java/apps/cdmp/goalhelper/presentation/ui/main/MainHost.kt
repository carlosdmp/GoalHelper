package apps.cdmp.goalhelper.presentation.ui.main

data class MainHost(val logo: MainButtonLogo = MainButtonLogo.HIDDEN)

enum class MainButtonLogo { HIDDEN, ADD, DONE }
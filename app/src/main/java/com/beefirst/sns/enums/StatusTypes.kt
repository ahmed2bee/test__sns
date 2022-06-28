package com.beefirst.sns.enums

class StatusTypes {

    companion object {
        fun statusTypes(type: Int): String {
            when (type) {
                0 -> return "Submitted"
                1 -> return "InProgress"
                2 -> return "Completed"
                3 -> return "Rejected"
            }
            return ""
        }
    }
}
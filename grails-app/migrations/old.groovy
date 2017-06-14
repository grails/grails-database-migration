databaseChangeLog = {

    changeSet(author: "jameskleeh", id: "activiti") {
        sqlFile(path: "activiti.oracle.create.engine.sql")
        sqlFile(path: "activiti.oracle.create.history.sql")
        sqlFile(path: "activiti.oracle.create.identity.sql")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-1") {
        createSequence(sequenceName: "hibernate_sequence")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-2") {
        createTable(tableName: "ACT_FSL_TASKAUDIT") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "ACT_FSL_TASKAUDITPK")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "task_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "event", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "assignee", type: "VARCHAR(255 CHAR)")
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-3") {
        createTable(tableName: "FSL_ROLES") {
            column(name: "id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "type", type: "VARCHAR(255 CHAR)")
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-4") {
        createTable(tableName: "FSL_USERS") {
            column(name: "id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "first_name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "account_expired", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "last_name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "enabled", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-5") {
        createTable(tableName: "FSL_USERS_ROLES") {
            column(name: "user_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "role_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-6") {
        createTable(tableName: "MD_COST_CENTER") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "MD_COST_CENTERPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "code", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "active", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-7") {
        createTable(tableName: "MD_EPS_TYPE") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "MD_EPS_TYPEPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "active", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-8") {
        createTable(tableName: "MD_INITIATIVE") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "MD_INITIATIVEPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "rank", type: "NUMBER(10, 0)") {
                constraints(nullable: "false")
            }

            column(name: "active", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-9") {
        createTable(tableName: "MD_MATERIAL_PART") {
            column(name: "id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "source_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-10") {
        createTable(tableName: "MD_METRIC") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "MD_METRICPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "initiative_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-11") {
        createTable(tableName: "MD_SAVINGS_METHOD") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "MD_SAVINGS_METHODPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "active", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "above_the_line", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-12") {
        createTable(tableName: "MD_SAVINGS_TYPE") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "MD_SAVINGS_TYPEPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "rank", type: "NUMBER(10, 0)") {
                constraints(nullable: "false")
            }

            column(name: "active", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "source_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-13") {
        createTable(tableName: "MD_SITE") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "MD_SITEPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "code", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "fmo", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "time_zone", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-14") {
        createTable(tableName: "MD_SOURCE") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "MD_SOURCEPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-15") {
        createTable(tableName: "MD_SYS_STATUS") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "MD_SYS_STATUSPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-16") {
        createTable(tableName: "action_item") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "action_itemPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "owner_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "due_date", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "project_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-17") {
        createTable(tableName: "business_unit") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "business_unitPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "code", type: "VARCHAR(255 CHAR)")

            column(name: "rank", type: "NUMBER(10, 0)") {
                constraints(nullable: "false")
            }

            column(name: "active", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "controller_id", type: "VARCHAR(255 CHAR)")
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-18") {
        createTable(tableName: "chartered_status") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "chartered_statusPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-19") {
        createTable(tableName: "document") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "documentPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "savings_project_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "url", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-20") {
        createTable(tableName: "financial_controller") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "financial_controllerPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "savings_percent", type: "NUMBER(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "savings_project_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "business_unit_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "approval_date", type: "date")

            column(name: "user_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-21") {
        createTable(tableName: "material_group_user") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "material_group_userPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "default_user", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "material_group_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-22") {
        createTable(tableName: "md_commodity") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "md_commodityPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "spend_area_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "source_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "direct", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-23") {
        createTable(tableName: "md_material_group") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "md_material_groupPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "cd", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "source_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-24") {
        createTable(tableName: "md_project_type") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "md_project_typePK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "rank", type: "NUMBER(10, 0)") {
                constraints(nullable: "false")
            }

            column(name: "active", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "source_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "business_type", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-25") {
        createTable(tableName: "md_spend_area") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "md_spend_areaPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-26") {
        createTable(tableName: "part") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "partPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "savings_project_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "num", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "type", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-27") {
        createTable(tableName: "reminder_history") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "reminder_historyPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "reminder_type", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "object_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-28") {
        createTable(tableName: "savings_project") {
            column(name: "id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "tech_group", type: "VARCHAR(255 CHAR)")

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "total_spend", type: "NUMBER(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "group_id", type: "VARCHAR(255 CHAR)")

            column(name: "quarterly_usage", type: "NUMBER(19, 2)")

            column(name: "chartered_status_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "commodity_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "platform", type: "VARCHAR(255 CHAR)")

            column(name: "send_to_final_approval", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "supplier", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "cost_center_id", type: "NUMBER(19, 0)")

            column(name: "approval_status", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "sbmt_approver_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "baseline_cost", type: "NUMBER(19, 2)")

            column(name: "external_project_id", type: "VARCHAR(255 CHAR)")

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "unit", type: "VARCHAR(255 CHAR)")

            column(name: "degree_of_confidence", type: "NUMBER(10, 0)")

            column(name: "material_group_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "unit_of_measure", type: "VARCHAR(255 CHAR)")

            column(name: "e_sourcing", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "sbmt_approval_date", type: "timestamp")

            column(name: "last_updated", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "requestor_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "owner_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "funding_type", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "savings_method_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "total_investment", type: "NUMBER(19, 2)")

            column(name: "sys_status_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "eps_type_id", type: "NUMBER(19, 0)")

            column(name: "project_type_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "package_group", type: "VARCHAR(255 CHAR)")

            column(name: "initiative_id", type: "NUMBER(19, 0)")

            column(name: "sourcing_mgr_id", type: "VARCHAR(255 CHAR)")

            column(name: "priority", type: "NUMBER(10, 0)")

            column(name: "metric_id", type: "NUMBER(19, 0)")

            column(name: "savings_type_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "site_id", type: "NUMBER(19, 0)")

            column(name: "description", type: "VARCHAR(1000 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "region", type: "VARCHAR(255 CHAR)")

            column(name: "targeted_supplier_num", type: "NUMBER(10, 0)")

            column(name: "ecommerce_type", type: "VARCHAR(255 CHAR)")

            column(name: "ariba_doc_number", type: "VARCHAR(255 CHAR)")

            column(name: "event_title", type: "VARCHAR(255 CHAR)")
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-29") {
        createTable(tableName: "savings_project_history") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "savings_project_historyPK")
            }

            column(name: "savings_project_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "history_status", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "new_value", type: "VARCHAR(255 CHAR)")

            column(name: "user_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "old_value", type: "VARCHAR(255 CHAR)")

            column(name: "field", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-30") {
        createTable(tableName: "savings_quarter") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "savings_quarterPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "savings_project_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "estimated", type: "NUMBER(19, 2)") {
                constraints(nullable: "false")
            }

            column(name: "num", type: "NUMBER(10, 0)") {
                constraints(nullable: "false")
            }

            column(name: "decision_date", type: "timestamp")

            column(name: "start_date", type: "date") {
                constraints(nullable: "false")
            }

            column(name: "approved", type: "NUMBER(1, 0)")

            column(name: "actual", type: "NUMBER(19, 2)")

            column(name: "year", type: "NUMBER(10, 0)") {
                constraints(nullable: "false")
            }

            column(name: "savings_quarters_idx", type: "NUMBER(10, 0)")
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-31") {
        createTable(tableName: "savings_quarter_history") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "savings_quarter_historyPK")
            }

            column(name: "savings_project_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "history_status", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "num", type: "NUMBER(10, 0)") {
                constraints(nullable: "false")
            }

            column(name: "new_value", type: "NUMBER(19, 2)")

            column(name: "user_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "old_value", type: "NUMBER(19, 2)")

            column(name: "year", type: "NUMBER(10, 0)") {
                constraints(nullable: "false")
            }

            column(name: "value_type", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-32") {
        createTable(tableName: "savings_timeline") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "savings_timelinePK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "savings_project_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "estimated", type: "date")

            column(name: "actual", type: "date")

            column(name: "status_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "savings_timelines_idx", type: "NUMBER(10, 0)")
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-33") {
        createTable(tableName: "savings_timeline_history") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "savings_timeline_historyPK")
            }

            column(name: "savings_project_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "history_status", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "new_value", type: "date")

            column(name: "user_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "status_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "old_value", type: "date")

            column(name: "value_type", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-34") {
        createTable(tableName: "splash_page") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "splash_pagePK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "content", type: "LONG") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-35") {
        createTable(tableName: "stakeholder") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "stakeholderPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "savings_project_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "business_unit_id", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "notify_only", type: "NUMBER(1, 0)") {
                constraints(nullable: "false")
            }

            column(name: "approval_date", type: "date")

            column(name: "user_id", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-36") {
        createTable(tableName: "stop_light") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "stop_lightPK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "yellow", type: "NUMBER(10, 0)")

            column(name: "green", type: "NUMBER(10, 0)") {
                constraints(nullable: "false")
            }

            column(name: "red", type: "NUMBER(10, 0)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-37") {
        createTable(tableName: "training_resource") {
            column(autoIncrement: "true", name: "id", type: "NUMBER(19, 0)") {
                constraints(primaryKey: "true", primaryKeyName: "training_resourcePK")
            }

            column(name: "version", type: "NUMBER(19, 0)") {
                constraints(nullable: "false")
            }

            column(name: "location", type: "VARCHAR(4000 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }

            column(name: "type", type: "VARCHAR(255 CHAR)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-38") {
        addPrimaryKey(columnNames: "id", constraintName: "FSL_ROLESPK", tableName: "FSL_ROLES")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-39") {
        addPrimaryKey(columnNames: "id", constraintName: "FSL_USERSPK", tableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-40") {
        addPrimaryKey(columnNames: "user_id, role_id", constraintName: "FSL_USERS_ROLESPK", tableName: "FSL_USERS_ROLES")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-41") {
        addPrimaryKey(columnNames: "id", constraintName: "MD_MATERIAL_PARTPK", tableName: "MD_MATERIAL_PART")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-42") {
        addPrimaryKey(columnNames: "id", constraintName: "savings_projectPK", tableName: "savings_project")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-43") {
        addUniqueConstraint(columnNames: "code", constraintName: "UC_BUSINESS_UNITCODE_COL", tableName: "business_unit")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-44") {
        addUniqueConstraint(columnNames: "authority", constraintName: "UC_FSL_ROLESAUTHORITY_COL", tableName: "FSL_ROLES")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-45") {
        addUniqueConstraint(columnNames: "username", constraintName: "UC_FSL_USERSUSERNAME_COL", tableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-46") {
        addUniqueConstraint(columnNames: "code", constraintName: "UC_MD_SITECODE_COL", tableName: "MD_SITE")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-47") {
        addUniqueConstraint(columnNames: "name", constraintName: "UC_MD_SYS_STATUSNAME_COL", tableName: "MD_SYS_STATUS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-48") {
        createIndex(indexName: "taskId_index", tableName: "ACT_FSL_TASKAUDIT") {
            column(name: "task_id")
        }
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-49") {
        addForeignKeyConstraint(baseColumnNames: "initiative_id", baseTableName: "MD_METRIC", constraintName: "FK11ej5elout2cwgqjl9q8hbqxq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_INITIATIVE")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-50") {
        addForeignKeyConstraint(baseColumnNames: "eps_type_id", baseTableName: "savings_project", constraintName: "FK17iqsisbmwa65c8nsng6d710g", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_EPS_TYPE")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-51") {
        addForeignKeyConstraint(baseColumnNames: "savings_method_id", baseTableName: "savings_project", constraintName: "FK1krxcurm352otr5uto8aa8hrk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_SAVINGS_METHOD")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-52") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "savings_quarter_history", constraintName: "FK22gi38mqjcgeu8xih00ymycnx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-53") {
        addForeignKeyConstraint(baseColumnNames: "savings_project_id", baseTableName: "financial_controller", constraintName: "FK4550ii36j08k70aw7q2rjyr8h", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "savings_project")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-54") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "material_group_user", constraintName: "FK5tjq803j8cn69r9l5eqgdqxnr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-55") {
        addForeignKeyConstraint(baseColumnNames: "requestor_id", baseTableName: "savings_project", constraintName: "FK6dhh0ubs9d3q4asm0o3vet935", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-56") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "FSL_USERS_ROLES", constraintName: "FK7d3w8e8u7vs6ogytoruudkq8b", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-57") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "financial_controller", constraintName: "FK800t1nr65gs3ohg09ogqof8n2", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-58") {
        addForeignKeyConstraint(baseColumnNames: "material_group_id", baseTableName: "material_group_user", constraintName: "FK84b6fh56jy4vf4fd35e0a10de", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "md_material_group")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-59") {
        addForeignKeyConstraint(baseColumnNames: "savings_project_id", baseTableName: "document", constraintName: "FK8brgyjo7m4kn1405he4qqrng", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "savings_project")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-60") {
        addForeignKeyConstraint(baseColumnNames: "business_unit_id", baseTableName: "stakeholder", constraintName: "FK8w9scfc8vlcg0vmy09h9cj9xi", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "business_unit")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-61") {
        addForeignKeyConstraint(baseColumnNames: "savings_project_id", baseTableName: "savings_quarter", constraintName: "FK9exkdxnobp25l80rwl9ogya37", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "savings_project")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-62") {
        addForeignKeyConstraint(baseColumnNames: "savings_type_id", baseTableName: "savings_project", constraintName: "FK9kqbg2w32qx88oijrwg9n32gr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_SAVINGS_TYPE")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-63") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "FSL_USERS_ROLES", constraintName: "FKb8pscxdanoh38hulmeb74cg70", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_ROLES")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-64") {
        addForeignKeyConstraint(baseColumnNames: "source_id", baseTableName: "MD_MATERIAL_PART", constraintName: "FKbbmahguj9jb82r5tcuhgwutlq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_SOURCE")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-65") {
        addForeignKeyConstraint(baseColumnNames: "savings_project_id", baseTableName: "savings_project_history", constraintName: "FKbo5kn8t4e3u3j775a19vxrk75", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "savings_project")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-66") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "savings_timeline_history", constraintName: "FKbuetqx2yj4qq2wpdblakewya5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-67") {
        addForeignKeyConstraint(baseColumnNames: "source_id", baseTableName: "md_project_type", constraintName: "FKc19jufw7uwbkwe8qvalslqlry", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_SOURCE")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-68") {
        addForeignKeyConstraint(baseColumnNames: "savings_project_id", baseTableName: "savings_timeline", constraintName: "FKdeyuuh3htqkpkswjy8nrjy5la", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "savings_project")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-70") {
        addForeignKeyConstraint(baseColumnNames: "owner_id", baseTableName: "savings_project", constraintName: "FKdy6k176rhyr38m167ucahukt1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-71") {
        addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "savings_timeline", constraintName: "FKedonjyx2dpxa7ndpa8uk5xqvb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "chartered_status")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-72") {
        addForeignKeyConstraint(baseColumnNames: "project_id", baseTableName: "action_item", constraintName: "FKen9k2nv16ouw784di72o5hx6e", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "savings_project")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-73") {
        addForeignKeyConstraint(baseColumnNames: "savings_project_id", baseTableName: "stakeholder", constraintName: "FKexrs9c6l93yl9axwat93ndask", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "savings_project")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-74") {
        addForeignKeyConstraint(baseColumnNames: "sbmt_approver_id", baseTableName: "savings_project", constraintName: "FKf72tunigr6f1tochd3m4vai6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-75") {
        addForeignKeyConstraint(baseColumnNames: "cost_center_id", baseTableName: "savings_project", constraintName: "FKf736nxmwc0xyp01xvjfanh61p", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_COST_CENTER")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-76") {
        addForeignKeyConstraint(baseColumnNames: "source_id", baseTableName: "md_material_group", constraintName: "FKfylald8vbmka39cvp4xleb7ee", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_SOURCE")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-77") {
        addForeignKeyConstraint(baseColumnNames: "source_id", baseTableName: "md_commodity", constraintName: "FKg4sumgkpkwxxjrryucp0or3qh", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_SOURCE")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-78") {
        addForeignKeyConstraint(baseColumnNames: "savings_project_id", baseTableName: "savings_quarter_history", constraintName: "FKhjsfmkiqn0anlqwph9iesg62f", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "savings_project")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-79") {
        addForeignKeyConstraint(baseColumnNames: "sourcing_mgr_id", baseTableName: "savings_project", constraintName: "FKhx66d4xmrj70lkl8ev8kxp15t", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-80") {
        addForeignKeyConstraint(baseColumnNames: "project_type_id", baseTableName: "savings_project", constraintName: "FKhyu6lcdj4mr2yephmogyhghyg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "md_project_type")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-81") {
        addForeignKeyConstraint(baseColumnNames: "sys_status_id", baseTableName: "savings_project", constraintName: "FKitpw8j0v15w0vq9b537ki68ej", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_SYS_STATUS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-82") {
        addForeignKeyConstraint(baseColumnNames: "spend_area_id", baseTableName: "md_commodity", constraintName: "FKk5wa0wnrwv87i877e52snaaao", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "md_spend_area")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-83") {
        addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "savings_project", constraintName: "FKk666w7ektce0o0jub55w4dsd0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_SITE")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-84") {
        addForeignKeyConstraint(baseColumnNames: "source_id", baseTableName: "MD_SAVINGS_TYPE", constraintName: "FKl9ckxncskt0dfusdnyua64sr3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_SOURCE")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-85") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "savings_project_history", constraintName: "FKldvhvvw2g0w1fhab0kitxvn3f", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-86") {
        addForeignKeyConstraint(baseColumnNames: "status_id", baseTableName: "savings_timeline_history", constraintName: "FKlskep6fesfmqkk7d8gglgt9d9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "chartered_status")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-87") {
        addForeignKeyConstraint(baseColumnNames: "business_unit_id", baseTableName: "financial_controller", constraintName: "FKmnswym5hv0je4le9npqi96ppc", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "business_unit")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-88") {
        addForeignKeyConstraint(baseColumnNames: "metric_id", baseTableName: "savings_project", constraintName: "FKnxkbykvllsn87wnvf4sydi19w", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_METRIC")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-89") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "stakeholder", constraintName: "FKobms95qetu0psnjtvh82604m0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-90") {
        addForeignKeyConstraint(baseColumnNames: "initiative_id", baseTableName: "savings_project", constraintName: "FKpfxaupiy8671tv0muscdaauaa", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "MD_INITIATIVE")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-91") {
        addForeignKeyConstraint(baseColumnNames: "owner_id", baseTableName: "action_item", constraintName: "FKplgrmcivagl8vb9ytm42mamgb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-92") {
        addForeignKeyConstraint(baseColumnNames: "material_group_id", baseTableName: "savings_project", constraintName: "FKpnxf579ft92tad3lguetxy34s", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "md_material_group")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-93") {
        addForeignKeyConstraint(baseColumnNames: "commodity_id", baseTableName: "savings_project", constraintName: "FKqtlqbjwd780pk9nc1qfsoif8q", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "md_commodity")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-94") {
        addForeignKeyConstraint(baseColumnNames: "controller_id", baseTableName: "business_unit", constraintName: "FKr5te6eqtfo4vguyxg4j86br9p", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "FSL_USERS")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-95") {
        addForeignKeyConstraint(baseColumnNames: "chartered_status_id", baseTableName: "savings_project", constraintName: "FKrtvp93i1uqb216fns3nb8h83c", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "chartered_status")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-96") {
        addForeignKeyConstraint(baseColumnNames: "savings_project_id", baseTableName: "savings_timeline_history", constraintName: "FKsd6j9o8meo4w1euu81xr05h0p", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "savings_project")
    }

    changeSet(author: "jameskleeh (generated)", id: "1496954876767-97") {
        addForeignKeyConstraint(baseColumnNames: "savings_project_id", baseTableName: "part", constraintName: "FKt62967bbitjbanlvn3q5lc26a", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "savings_project")
    }

    changeSet(author: "jameskleeh", id: "semantic-layer") {
        createView(viewName: 'SAVINGS_PIPELINE_SEMANTIC_VIEW', replaceIfExists: "true", selectQuery: """
                select sp.id, 
                       sp.tech_group as tech_group_fmo, 
                       sp.date_created, 
                       sp.total_spend, 
                       sp.group_id, 
                       sp.quarterly_usage,
                       cs.name as chartered_status,
                       sa.name as commodity_spend_area,
                       c.name as commodity_name,
                       c.direct as commodity_type,
                       sp.platform as platform_fmo,
                       sp.SEND_TO_FINAL_APPROVAL as send_to_final_approval_bln,
                       sp.SUPPLIER,
                       cc.CODE as cost_center_code,
                       cc.NAME as cost_center_name,
                       cc_site.CODE as cost_center_site,
                       sp.APPROVAL_STATUS,
                       sbmt.first_name || ' ' || sbmt.last_name as SBMT_Name,
                       sbmt.username as SBMT_UID,
                       sp.baseline_cost,
                       sp.external_project_id,
                       sp.name as project_name,
                       sp.unit,
                       sp.unit_of_measure,
                       sp.degree_of_confidence,
                       mg.DESCRIPTION as material_group_description,
                       mg.CD as material_group_code,
                       sp.E_SOURCING as e_sourcing_bln,
                       sp.LAST_UPDATED,
                       req.first_name || ' ' || req.last_name as requestor_name,
                       req.username as requestor_uid,
                       ownr.first_name || ' ' || ownr.last_name as owner_name,
                       ownr.username as owner_uid,
                       sp.funding_type,
                       sm.ABOVE_THE_LINE as ATL_bln,
                       sm.NAME as savings_method,
                       sp.TOTAL_INVESTMENT,
                       ss.NAME as system_status,
                       et.NAME as eps_type,
                       pt_source.NAME as project_type_source,
                       pt.NAME as project_type,
                       pt.BUSINESS_TYPE as project_type_business,
                       sp.PACKAGE_GROUP as package_group_fmo,
                       i.NAME as initiative,
                       src_mgr.first_name || ' ' || src_mgr.last_name as sourcing_manager,
                       src_mgr.username as sourcing_manager_uid,
                       sp.PRIORITY,
                       m.NAME as metric,
                       st.NAME as savings_type,
                       st.DESCRIPTION as savings_type_description,
                       s.NAME as site,
                       s.CODE as site_code,
                       s.TIME_ZONE as site_time_zone,
                       s.FMO as site_fmo_bln,
                       sp.description as project_description,
                       sp.REGION as e_sourcing_region,
                       sp.TARGETED_SUPPLIER_NUM as e_sourcing_num_suppliers,
                       sp.ECOMMERCE_TYPE as e_sourcing_e_commerce_type,
                       sp.ARIBA_DOC_NUMBER as e_sourcing_ariba_doc_num,
                       sp.EVENT_TITLE as e_sourcing_event_title,       
                       sp.QUARTERLY_USAGE * sp.BASELINE_COST as quarterly_cost,
                       (select sum(estimated) from savings_quarter where savings_project_id = sp.id) as total_estimated_savings,
                       (select sum(actual) from savings_quarter where savings_project_id = sp.id) as total_actual_savings,
                       (select count(1) from savings_quarter where savings_project_id = sp.id) as number_of_savings_quarters,
                       (select count(1) from document where savings_project_id = sp.id) as number_of_docs_attached,
                       il1.ESTIMATED as il1_est_completion_date,
                       il2.ESTIMATED as il2_est_completion_date,
                       il3.ESTIMATED as il3_est_completion_date,
                       il4.ESTIMATED as il4_est_completion_date,
                       il5.ESTIMATED as il5_est_completion_date,
                       il1.ACTUAL as il1_actual_completion_date,
                       il2.ACTUAL as il2_actual_completion_date,
                       il3.ACTUAL as il3_actual_completion_date,
                       il4.ACTUAL as il4_actual_completion_date,
                       il5.ACTUAL as il5_actual_completion_date,
                       (select count(1) from stakeholder where savings_project_id = sp.id and notify_only = 0) as num_of_stakeholder_approvers,
                       (select count(1) from stakeholder where savings_project_id = sp.id and notify_only = 1) as num_of_stakeholder_notif,
                       (select count(1) from financial_controller where savings_project_id = sp.id) as num_fin_controllers,
                       (select count(1) from part where savings_project_id = sp.id) as number_of_parts,
                       (select count(1) from action_item ai where ai.project_id = sp.id) as total_num_of_action_items,
                       (select count(1) from action_item ai where ai.project_id = sp.id and (ai.status = 'IN_PROGRESS' or ai.status = 'HOLD')) as num_open_action_items
           
                from savings_project sp
                left outer join chartered_status cs on cs.id = sp.chartered_status_id
                left outer join md_commodity c on c.id = sp.commodity_id
                left outer join md_spend_area sa on sa.id = c.spend_area_id
                left outer join md_cost_center cc on cc.id = sp.cost_center_id
                left outer join md_site cc_site on cc_site.id = cc.id
                left outer join fsl_users sbmt on sbmt.id = sp.sbmt_approver_id
                left outer join MD_MATERIAL_GROUP mg on mg.id = sp.material_group_id
                left outer join fsl_users req on req.id = sp.REQUESTOR_ID
                left outer join fsl_users ownr on ownr.id = sp.owner_id
                left outer join md_savings_method sm on sm.id = sp.savings_method_id
                left outer join md_sys_status ss on ss.id = sp.sys_status_id
                left outer join md_eps_type et on et.id = sp.eps_type_id
                left outer join md_project_type pt on pt.id = sp.project_type_id
                left outer join md_source pt_source on pt_source.id = pt.source_id
                left outer join md_initiative i on i.id = sp.initiative_id
                left outer join fsl_users src_mgr on src_mgr.id = sp.sourcing_mgr_id
                left outer join md_metric m on m.id = sp.metric_id
                left outer join md_savings_type st on st.id = sp.savings_type_id
                left outer join md_site s on s.id = sp.site_id
                left outer join savings_timeline il1 on il1.savings_project_id = sp.id
                left outer join savings_timeline il2 on il2.savings_project_id = sp.id
                left outer join savings_timeline il3 on il3.savings_project_id = sp.id
                left outer join savings_timeline il4 on il4.savings_project_id = sp.id
                left outer join savings_timeline il5 on il5.savings_project_id = sp.id
            """)

    }
}

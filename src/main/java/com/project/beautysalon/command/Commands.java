package com.project.beautysalon.command;

import com.project.beautysalon.command.common.*;
import com.project.beautysalon.command.client.*;
import com.project.beautysalon.command.admin.*;
import com.project.beautysalon.command.master.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Commands {

    REGISTER_CLIENT("registerClient", new RegisterClientCommand(), List.of(CommandAccess.GUEST)),
    LOGIN("login", new LoginCommand(), List.of(CommandAccess.GUEST)),
    VIEW_CLIENTS_LIST("viewClientsList", new ViewClientsCommand(), List.of(CommandAccess.ADMIN)),
    BLOCK_CLIENT("blockClient", new BlockClientCommand(), List.of(CommandAccess.ADMIN)),
    UNBLOCK_CLIENT("unblockClient", new UnblockClientCommand(), List.of(CommandAccess.ADMIN)),
    REGISTER_MASTER("registerMaster", new RegisterMasterCommand(), List.of(CommandAccess.ADMIN)),
    VIEW_MASTERS_LIST("viewMastersList", new ViewMastersCommand(), List.of(CommandAccess.ADMIN)),
    BLOCK_MASTER("blockMaster", new BlockMasterCommand(), List.of(CommandAccess.ADMIN)),
    UNBLOCK_MASTER("unblockMaster", new UnblockMasterCommand(), List.of(CommandAccess.ADMIN)),
    ADD_NEW_CATEGORY("addNewCategory", new AddNewCategoryCommand(), List.of(CommandAccess.ADMIN)),
    VIEW_CATEGORIES_LIST("viewCategoriesList", new ViewCategoriesCommand(), List.of(CommandAccess.ADMIN)),
    DELETE_CATEGORY("deleteCategory", new DeleteCategoryCommand(), List.of(CommandAccess.ADMIN)),
    EDIT_CATEGORY_FORM("editCategoryForm", new EditCategoryFormCommand(), List.of(CommandAccess.ADMIN)),
    EDIT_CATEGORY("editCategory", new EditCategoryCommand(), List.of(CommandAccess.ADMIN)),
    VIEW_SERVICES_LIST("viewServicesList", new ViewServicesAdminCommand(), List.of(CommandAccess.ADMIN)),
    ADD_NEW_SERVICE_FORM("addNewServiceForm", new AddNewServiceFormCommand(), List.of(CommandAccess.ADMIN)),
    ADD_NEW_SERVICE("addNewService", new AddNewServiceCommand(), List.of(CommandAccess.ADMIN)),
    EDIT_SERVICE_FORM("editServiceForm", new EditServiceFormCommand(), List.of(CommandAccess.ADMIN)),
    EDIT_SERVICE("editService", new EditServiceCommand(), List.of(CommandAccess.ADMIN)),
    DELETE_SERVICE("deleteService", new DeleteServiceCommand(), List.of(CommandAccess.ADMIN)),
    EDIT_MASTER_CATEGORIES_FORM("editMasterCategoriesForm", new EditMasterCategoriesFormCommand(), List.of(CommandAccess.ADMIN)),
    EDIT_MASTER_CATEGORIES("editMasterCategories", new EditMasterCategoriesCommand(), List.of(CommandAccess.ADMIN)),
    VIEW_SCHEDULE("viewSchedule", new ViewScheduleAdminCommand(), List.of(CommandAccess.ADMIN)),
    LOGOUT("logout", new LogoutCommand(), List.of(CommandAccess.GUEST, CommandAccess.CLIENT, CommandAccess.MASTER, CommandAccess.ADMIN)),
    EDIT_PROFILE("editProfile", new EditProfileCommand(), List.of(CommandAccess.CLIENT, CommandAccess.MASTER, CommandAccess.ADMIN)),
    CHANGE_PASSWORD("changePassword", new ChangePasswordCommand(), List.of(CommandAccess.CLIENT, CommandAccess.MASTER, CommandAccess.ADMIN)),
    TOP_UP_BALANCE("topUpBalance", new TopUpBalanceCommand(), List.of(CommandAccess.CLIENT)),
    VIEW_WORKING_DAYS("viewWorkingDays", new ViewWorkingDaysCommand(), List.of(CommandAccess.MASTER)),
    EDIT_WORKING_DAYS_FORM("editWorkingDaysForm", new EditWorkingDaysFormCommand(), List.of(CommandAccess.MASTER)),
    EDIT_WORKING_DAYS("editWorkingDays", new EditWorkingDaysCommand(), List.of(CommandAccess.MASTER)),
    VIEW_SERVICES("viewServices", new ViewServicesClientCommand(), List.of(CommandAccess.GUEST, CommandAccess.CLIENT)),
    VIEW_MASTERS("viewMasters", new ViewMastersClientCommand(), List.of(CommandAccess.GUEST, CommandAccess.CLIENT)),
    BOOK_APPOINTMENT_FORM("bookAppointmentForm", new BookAppointmentFormCommand(), List.of(CommandAccess.GUEST, CommandAccess.CLIENT)),
    BOOK_APPOINTMENT("bookAppointment", new BookAppointmentCommand(), List.of(CommandAccess.GUEST, CommandAccess.CLIENT)),
    VIEW_APPOINTMENT_REQUESTS("viewAppointmentRequests", new ViewAppointmentRequestsCommand(), List.of(CommandAccess.ADMIN)),
    CONFIRM_REQUEST("confirmRequest", new ConfirmRequestCommand(), List.of(CommandAccess.ADMIN)),
    EDIT_TIMESLOT("editTimeslot", new EditTimeslotCommand(), List.of(CommandAccess.ADMIN)),
    DECLINE_REQUEST("declineRequest", new DeclineRequestCommand(), List.of(CommandAccess.ADMIN)),
    VIEW_BOOKED_APPOINTMENTS("viewBookedAppointments", new ViewAppointmentsMasterCommand(), List.of(CommandAccess.MASTER)),
    COMPLETE_APPOINTMENT("completeAppointment", new CompleteAppointmentCommand(), List.of(CommandAccess.MASTER)),
    VIEW_MASTER_SCHEDULE("viewMasterSchedule", new ViewScheduleMasterCommand(), List.of(CommandAccess.MASTER)),
    CANCEL_APPOINTMENT("cancelAppointment", new CancelAppointmentCommand(), List.of(CommandAccess.ADMIN)),
    VIEW_APPOINTMENTS("viewAppointments", new ViewAppointmentsClientCommand(), List.of(CommandAccess.CLIENT)),
    VIEW_MASTER_INFO("viewMasterInfo", new ViewMasterInfoCommand(), List.of(CommandAccess.GUEST, CommandAccess.CLIENT)),
    ADD_REVIEW("addReview", new AddReviewCommand(), List.of(CommandAccess.CLIENT)),
    CHANGE_REVIEW_VISIBILITY("changeReviewVisibility", new ChangeReviewVisibilityCommand(), List.of(CommandAccess.ADMIN)),
    VIEW_REVIEWS("viewReviews", new ViewReviewsCommand(), List.of(CommandAccess.CLIENT, CommandAccess.MASTER, CommandAccess.ADMIN)),
    CHANGE_LANGUAGE("changeLang", new ChangeLanguageCommand(), List.of(CommandAccess.GUEST, CommandAccess.CLIENT, CommandAccess.MASTER, CommandAccess.ADMIN));

    private final  String commandName;
    private final Command command;
    private final List<CommandAccess> accessLevel;
    public static final Map<String, List<CommandAccess>> COMMAND_ACCESS_MAP = new HashMap<>();
    public static final Map<String, Command> NAME_COMMAND_MAP = new HashMap<>();

    static {
        for(var command: values()) {
            COMMAND_ACCESS_MAP.put(command.commandName, command.accessLevel);
            NAME_COMMAND_MAP.put(command.commandName, command.command);
        }
    }

    Commands(String commandName, Command command, List<CommandAccess> accessLevel) {
        this.commandName = commandName;
        this.command = command;
        this.accessLevel = accessLevel;
    }

}

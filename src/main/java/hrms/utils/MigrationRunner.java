// package hrms.utils;

// import hrms.dao.AccountDAO;

// public class MigrationRunner {

//     public static void main(String[] args) {
//         AccountDAO accountDAO = new AccountDAO();

//         System.out.println("Starting password migration...");

//         boolean allHashed = accountDAO.areAllPasswordsHashed();
//         System.out.println("All passwords hashed: " + allHashed);

//         if (!allHashed) {
//             int count = accountDAO.migratePasswords();
//             System.out.println("Migration completed! Total migrated: " + count);
//         } else {
//             System.out.println("All passwords are already hashed.");
//         }
//     }
// }

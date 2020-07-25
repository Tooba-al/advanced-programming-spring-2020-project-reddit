package com.reddit.Pages;

import com.reddit.Modules.MessageManagement.Messagable;
import com.reddit.Modules.MessageManagement.Message;
import com.reddit.Modules.MessageManagement.MessageType;
import com.reddit.Modules.SubredditManagement.Post;
import com.reddit.Modules.SubredditManagement.Subreddit;
import com.reddit.Modules.UserManagement.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HomePage {
    private User currentUser;
    private Subreddit subreddit;
    private Scanner scanner = new Scanner(System.in);

    public HomePage(User currentUser) {
        this.currentUser = currentUser;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public Subreddit getSubreddit() {
        return subreddit;
    }
    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }

    // create home page
    public static HomePage createHomePage(User currentUser) {
        return new HomePage(currentUser);
    }

    // Search menu
    public void searchMenu(){
        System.out.println("Search by:");
        System.out.println("1. User\n2. Subreddit\n3. Subreddit's Post");
    }

    // search functions
    public void searching(){
        searchMenu();
        System.out.println();
        System.out.print("Choose a number: ");
        int searchChoice = scanner.nextInt();
        scanner.nextLine();

        switch (searchChoice){
            case 1:
                int count = 1;
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                ArrayList<User> users = User.searchUser(username);
                if (users.size() == 0){
                    System.out.println("Not Found!");
                }
                else {
                    System.out.println("Search Result:");
                    for (User user : users){
                        System.out.print(count + ". ");
                        System.out.println("Username: " + user.getUsername());
                        ++count;
                    }
                    System.out.println();
                    System.out.print("Choose a user: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    User resultUser = users.get(choice-1);
                    System.out.println();

                    boolean hasFollowed = false;
                    for (User user : currentUser.getUserRelationUser()){
                        if (user == resultUser){
                            hasFollowed = true;
                        }
                    }
                    if (hasFollowed == false){
                        System.out.println("1. Follow\n2. Show user subreddits");
                    }
                    else {
                        System.out.println("1. Unfollow\n2. Show user subreddits");
                    }
                    System.out.println();
                    System.out.print("Choose a number: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice){
                        case 1:
                            if (hasFollowed == false){
                                currentUser.follow(currentUser, resultUser);
                            }
                            else {
                                currentUser.unfollow(currentUser, resultUser);
                            }
                            break;
                        case 2:
                            count = 1;
                            for (Subreddit subreddit : resultUser.getUserSubreddits()){
                                System.out.println(count + ".");
                                Subreddit.showSubreddit(subreddit);
                                ++count;
                            }
                            if (count == 1){
                                System.out.println("Not Found!");
                            }else {
                                System.out.println();
                                System.out.print("Choose a subreddit to show: ");
                                choice = scanner.nextInt();
                                scanner.nextLine();
                                Subreddit subreddit = resultUser.getUserSubreddits().get(choice - 1);
                                SubredditPage subredditPage = SubredditPage.createSubredditPage(currentUser, subreddit);
                                subredditPage.run();
                            }
                            break;
                        default:
                            searching();
                            break;
                    }
                }
                break;
            case 2:
                count = 1;
                System.out.print("Subreddit Name: ");
                String subredditName = scanner.nextLine();
                ArrayList<Subreddit> subreddits = Subreddit.searchSubreddit(subredditName);
                if (subreddits.size() == 0){
                    System.out.println("Not Found!");
                }
                else {
                    System.out.println("Search Result:");
                    for (Subreddit subreddit : subreddits){
                        System.out.println(count + ". ");
                        Subreddit.showSubredditDetail(subreddit);
                        System.out.println();
                        ++count;
                    }
                    System.out.println();
                    System.out.print("Choose a post to show: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    Subreddit resultSubreddit = subreddits.get(choice-1);
                    SubredditPage subredditPage = SubredditPage.createSubredditPage(currentUser, resultSubreddit);
                    subredditPage.run();
                }
                break;
            case 3:
                count = 1;
                System.out.print("Post Title: ");
                String postTitle = scanner.nextLine();
                ArrayList<Post> result = Post.searchPost(postTitle);
                if (result.size() == 0){
                    System.out.println("Not Found!");
                }
                else {
                    System.out.println("Search Result:");
                    for (Post post : result){
                        System.out.print(count + ". ");
                        Post.showPost(post);
                        System.out.println();
                        ++count;
                    }
                    System.out.println();
                    System.out.print("Choose a post to show: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    Post resultPost = result.get(choice-1);
                    PostPage postPage = PostPage.createPostPage(currentUser, subreddit, resultPost);
                    postPage.run();
                }
                break;
            default:
                searching();
                break;
        }
    }

    // get user messages
    private void getUserMessages() {
        List<Message> messages = Message.getMessagableMessages(currentUser);
        if(messages.isEmpty()){
            System.out.println("Empty!");
            return;
        }
        for (Message message : messages) {
            System.out.println(message.getMessageData().show());
            System.out.println(message.getCalendar());
            List<Messagable> messagables = message.getSenders();
            System.out.println("-------- SENDERS --------");
            System.out.println();
            for (Messagable messagable : messagables) {
                System.out.println(messagable.getString());
            }

            System.out.println("-------------------------");
        }
    }

    // run
    public void run(){
        //Clean Console
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("     [Home Page]    ");
        System.out.println("--------------------");
        System.out.println("1. Share Subreddit\n2. Show Subreddit Popular Posts\n3. Search\n4. Shared Post\n5. Show messages\n6. Send message\n7. Exit");
        System.out.println();
        System.out.print("Choose a number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                Subreddit newSubreddit = Subreddit.shareSubreddit(currentUser);
                SubredditPage subredditPage = SubredditPage.createSubredditPage(currentUser, newSubreddit);
                subredditPage.run();
                run();
            case 2:
                Post popularPost = Subreddit.showPopularSubredditPosts(currentUser);
                if (popularPost == null){
                    System.out.println("No Data Yet!");
                    run();
                }
                else {
                    PostPage postPage = PostPage.createPostPage(currentUser, subreddit, popularPost);
                    postPage.run();
                }
                break;
            case 3:
                searching();
                run();
                break;
            case 4:
                int count = 1;
                for (Post post : currentUser.getSharedPosts()){
                    System.out.println(count + ".");
                    Post.showPost(post);
                }
                if (count == 1){
                    System.out.println("No Data Yet!");
                }
                else {
                    System.out.println();
                    System.out.print("Choose a post to show: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    Post post = currentUser.getSharedPosts().get(choice);
                    PostPage postPage = PostPage.createPostPage(currentUser, subreddit, post);
                    postPage.run();
                }
                run();
                break;
            case 5:
                getUserMessages();
                break;
            case 6:
                MessagePage messageMainView = new MessagePage(currentUser);
                messageMainView.run(MessageType.Ordinary);
                break;
            case 7:
                LoginSignupPage loginSignupPage = LoginSignupPage.creatLoginSignupPage();
                loginSignupPage.run();
                break;
            default:
                run();
                break;
        }

    }
}

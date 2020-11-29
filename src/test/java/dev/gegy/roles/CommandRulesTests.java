package dev.gegy.roles;

import dev.gegy.roles.override.command.CommandPermissionRules;
import dev.gegy.roles.override.command.MatchableCommand;
import dev.gegy.roles.override.command.PermissionResult;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class CommandRulesTests {
    @Test
    void testAllowExecuteAsDenyExecute() {
        CommandPermissionRules rules = CommandPermissionRules.builder()
                .add(matcher("execute as"), PermissionResult.ALLOW)
                .add(matcher("execute"), PermissionResult.DENY)
                .build();

        assertEquals(rules.test(command("execute as")), PermissionResult.ALLOW);
        assertEquals(rules.test(command("execute at")), PermissionResult.DENY);
        assertEquals(rules.test(command("execute")), PermissionResult.ALLOW);
    }

    @Test
    void testAllowExecuteDenyExecuteAs() {
        CommandPermissionRules rules = CommandPermissionRules.builder()
                .add(matcher("execute as"), PermissionResult.DENY)
                .add(matcher("execute"), PermissionResult.ALLOW)
                .build();

        assertEquals(rules.test(command("execute as")), PermissionResult.DENY);
        assertEquals(rules.test(command("execute at")), PermissionResult.ALLOW);
        assertEquals(rules.test(command("execute")), PermissionResult.ALLOW);
    }

    private static Pattern[] matcher(String matcher) {
        String[] patternStrings = matcher.split(" ");
        Pattern[] patterns = new Pattern[patternStrings.length];
        for (int i = 0; i < patternStrings.length; i++) {
            patterns[i] = Pattern.compile(patternStrings[i]);
        }
        return patterns;
    }

    private static MatchableCommand command(String command) {
        return MatchableCommand.parse(command);
    }
}
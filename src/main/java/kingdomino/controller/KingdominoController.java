package kingdomino.controller;

import kingdomino.model.Domino;
import kingdomino.model.DominoSelection;
import kingdomino.model.Draft;
import kingdomino.model.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KingdominoController {
    public static class Helper {
        public static void calculate_bonus_score_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void calculate_player_score_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void calculate_property_attributes_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void calculate_ranking_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static boolean create_next_draft_is_initiated() {
            final Game game = kingdomino.helper.Helper.getCurrentGame();
            if (game.getTopDominoInPile() != null) {
                final Draft nextDraft = new Draft(Draft.DraftStatus.FaceDown, game);
                game.setCurrentDraft(game.getNextDraft());
                game.setNextDraft(nextDraft);

                for (int i = 0; i < 4; ++i) {
                    Domino topDominoInPile = game.getTopDominoInPile();
                    nextDraft.addIdSortedDomino(topDominoInPile);
                    topDominoInPile = topDominoInPile.getNextDomino();
                    game.setTopDominoInPile(topDominoInPile);
                }

                return true;
            }

            return false;
        }

        public static void the_properties_of_the_player_are_identified() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void each_tile_placement_is_valid() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void the_game_result_is_not_yet_final() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void the_ordering_of_the_dominoes_in_the_next_draft_is_initiated() {
            final Draft nextDraft = kingdomino.helper.Helper.getCurrentGame().getNextDraft();
            final List<Domino> dominos = new ArrayList<>(nextDraft.getIdSortedDominos());
            dominos.sort(Comparator.comparingInt(Domino::getId));

            for (Domino d : dominos)
                nextDraft.removeIdSortedDomino(d);
            for (Domino d : dominos)
                nextDraft.addIdSortedDomino(d);

            nextDraft.setDraftStatus(Draft.DraftStatus.Sorted);
        }

        public static void the_revealing_of_the_dominoes_in_the_next_draft_is_initiated() {
            kingdomino.helper.Helper.getCurrentGame().getNextDraft().setDraftStatus(Draft.DraftStatus.FaceUp);
        }

        public static void i_initiate_to_arrange_the_domino_in_the_fixed_order(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void next_draft_is_sorted() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void next_draft_is_revealed() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void reveal_first_draft_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void check_castle_adjacency_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void validation_of_the_grid_size_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void check_current_preplaced_domino_adjacency_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void check_current_preplaced_domino_overlapping_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void i_initiate_the_browsing_of_all_dominoes() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void i_provide_a_domino_ID(Integer int1) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void i_initiate_the_browsing_of_all_dominoes_of_terrain_type(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void the_current_player_places_his_her_domino() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void the_current_player_discards_his_her_domino() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void current_player_chooses_to_place_king_on(Integer dominoId) {
            final Domino domino = kingdomino.helper.Helper.getdominoByID(dominoId);
            if (domino.getDominoSelection() == null) {
                if (kingdomino.helper.Helper.getCurrentGame().getNextDraft().getIdSortedDominos().contains(domino)) {
                    new DominoSelection(kingdomino.helper.Helper.getCurrentGame().getCurrentPlayer(), domino, kingdomino.helper.Helper.getCurrentGame().getNextDraft());
                }
            }
        }

        public static void start_of_the_game_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void i_initiate_loading_a_saved_game_from(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void removes_his_king_from_the_domino(String string, Integer int1) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void requests_to_move_the_domino(String string, String string2) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void requests_to_place_the_selected_domino(String string, Integer int1) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void i_provide_my_username_and_initiate_creating_a_new_user(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void i_initiate_the_browsing_of_all_users() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void i_initiate_querying_the_game_statistics_for_a_user(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void requests_to_rotate_the_domino_with(String string, String string2) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void the_user_initiates_saving_the_game_to_a_file_named(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void the_user_agrees_to_overwrite_the_existing_file_named(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void the_player_completes_his_her_domino_selection(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void the_player_completes_his_her_domino_selection_of_the_game(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void set_game_options_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void the_number_of_players_is_set_to(Integer int1) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void harmony_selected_as_bonus_option(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void middle_Kingdom_selected_as_bonus_option(String string) {
            // Write code here that turns the phrase above into concrete actions
        }

        public static void the_shuffling_of_dominoes_is_initiated() {
            List<Domino> dominos = new ArrayList<>(kingdomino.helper.Helper.getCurrentGame().getAllDominos());
            Collections.shuffle(dominos);

            kingdomino.helper.Helper.getCurrentGame().setTopDominoInPile(dominos.get(0));
            for (int i = 0; i < dominos.size() - 1; ++i) {
                dominos.get(i).setNextDomino(dominos.get(i + 1));
            }
        }

        public static void starting_a_new_game_is_initiated() {
            // Write code here that turns the phrase above into concrete actions
        }
    }
}

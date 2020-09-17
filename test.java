public class test {

    public static void main(final String[] args) {
        SymbolTable st = new SymbolTable();

        st.put("hje", 'c');
        st.dump();
        System.out.print("\n");
        st.put("hej", 'w');
        st.dump();
        System.out.print("\n" + st.size() + "\n");
        if (st.size() == 2) {
            System.out.print("\n" + "ok" + "\n");
        }
        System.out.print("\n");
        st.put("hej", 'c');
        st.put("pje", 'd');
        if (st.size() == 3) {
            System.out.print("\n" + "ok" + "\n");
        }
        st.put("pje", null);
        if (st.size() == 2) {
            System.out.print("\n" + "ok" + "\n");
        }
        st.put("dge", 'g');
        st.put("alkje", 't');
        st.put("ploke", 'a');
        st.put("lld", 'w');
        if (st.size() == 6) {
            System.out.print("\n" + "ok" + "\n");
        }
        st.dump();
        System.out.print("\n");
        st.delete("hje");
        st.dump();
        System.out.print("\n");
        st.put("test", 'p');
        st.dump();
        System.out.print("\n");
        System.out.print(st.get("test") + "\n");
        System.out.print(st.get("hej") + "\n");
        st.put("key", 'q');
    }

}

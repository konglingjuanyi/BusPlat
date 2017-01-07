/**
 * Created by wangqinghui on 2017/1/5.
 */
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.CmdLineException;
public class sss {

    public static void main(String[] args) {
        Business bean = new Business();
        CmdLineParser parser = new CmdLineParser(bean);

        args = "-name ddd".split("\\s+?");

        try {
            parser.parseArgument(args);
            bean.run();
        } catch (CmdLineException e) {
            // handling of wrong arguments
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }
}

import { Link } from "react-router-dom";

function Header() {
  return (
    <header className="bg-white shadow-sm">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          <Link to="/" className="text-xl font-bold text-gray-900">
            Web App Sample
          </Link>
          <nav className="flex space-x-4">
            <Link
              to="/users"
              className="text-gray-600 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium"
            >
              ユーザーリスト
            </Link>
            <a
              href="/swagger-ui.html"
              target="_blank"
              rel="noopener noreferrer"
              className="text-gray-600 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium"
            >
              Swagger
            </a>
          </nav>
        </div>
      </div>
    </header>
  );
}

export default Header;
